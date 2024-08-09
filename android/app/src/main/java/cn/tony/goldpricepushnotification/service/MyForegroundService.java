package cn.tony.goldpricepushnotification.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import java.util.Arrays;
import java.util.Date;

import cn.tony.goldpricepushnotification.R;
import cn.tony.goldpricepushnotification.dao.GoldPriceDao;
import cn.tony.goldpricepushnotification.database.AppDatabase;
import cn.tony.goldpricepushnotification.entity.GoldPrice;
import cn.tony.goldpricepushnotification.entity.GoldPriceAlert;
import cn.tony.goldpricepushnotification.widget.DemoWidgetProvider;


public class MyForegroundService extends Service {
    AppDatabase appDatabase;
    private static final String CHANNEL_GOLD_PRICE_NOTIFY_NORMAL = "GOLD_PRICE_NOTIFY_NORMAL";
    private static final String CHANNEL_GOLD_PRICE_NOTIFY_IMPORTANT = "GOLD_PRICE_NOTIFY_IMPORTANT";

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = AppDatabase.getInstance(this);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 这里可以启动一个后台线程来更新状态栏数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                GoldService goldService = new GoldService();
                while (true) {
                    // 更新状态栏通知的数据
                    GoldPrice goldPrice = goldService.getCnBankGoldPrice();
                    updateNotification(String.format("买入价：%s, 卖出价：%s", goldPrice.getBid(), goldPrice.getSell()));
                    sendUpdateBroadcastToUpdateGoldPrice(goldPrice);
                    try {
                        Thread.sleep(10000); // 每60秒更新一次数据
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    /*    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Service is running")
                .setSmallIcon(R.drawable.icon)
                .build();*/

//        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Foreground Service Stopped", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateNotification(String newContent) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_GOLD_PRICE_NOTIFY_NORMAL)
                .setContentTitle("实时金价")
                .setContentText(newContent)
                .setSmallIcon(R.drawable.icon)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel normalChannel = new NotificationChannel(
                    CHANNEL_GOLD_PRICE_NOTIFY_NORMAL,
                    "实时金价通知",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationChannel importChannel = new NotificationChannel(
                    CHANNEL_GOLD_PRICE_NOTIFY_IMPORTANT,
                    "金价升值降价通知",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannels(
                    Arrays.asList(new NotificationChannel[]{normalChannel, importChannel})
            );
        }
    }
    private String getData() {
        // 模拟获取数据的操作
        return "Updated Data" + new Date().toString();
    }


    private void sendUpdateBroadcastToUpdateGoldPrice(GoldPrice goldPrice) {
        GoldPriceDao goldPriceDao = appDatabase.goldPriceDao();
        goldPriceDao.insertAll(goldPrice);
        Intent intent = new Intent();
        intent.setAction("cn.tony.gold.price");
        intent.putExtra("goldPrice", goldPrice);
        sendBroadcast(intent);
        intent.setComponent(new ComponentName(this, DemoWidgetProvider.class));
        sendBroadcast(intent);
        alertUser(goldPrice);
    }

    public void alertUser(GoldPrice goldPrice){
        GoldPriceAlert goldPriceAlert = appDatabase.goldPriceAlertDao().getById(1);
        if (goldPrice.getBid() < goldPriceAlert.getLeftPoint()){
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_GOLD_PRICE_NOTIFY_IMPORTANT)
                    .setContentTitle(String.format("黄金清仓大甩卖", goldPrice.getBid(), goldPrice.getBid(), goldPrice.getBid()))
                    .setContentText(String.format("买入价：%s, 卖出价：%s", goldPrice.getBid(), goldPrice.getSell()))
                    .setSmallIcon(R.drawable.icon)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, notification);
        }
        if (goldPrice.getSell() > goldPriceAlert.getRightPoint()){
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_GOLD_PRICE_NOTIFY_IMPORTANT)
                    .setContentTitle(String.format("黄金涨价", goldPrice.getSell(), goldPrice.getSell(), goldPrice.getSell()))
                    .setContentText(String.format("买入价：%s, 卖出价：%s", goldPrice.getBid(), goldPrice.getSell()))
                    .setSmallIcon(R.drawable.icon)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, notification);
        }
    }


}