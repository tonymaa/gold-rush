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

import java.util.Date;

import cn.tony.goldpricepushnotification.R;
import cn.tony.goldpricepushnotification.entity.GoldPrice;
import cn.tony.goldpricepushnotification.widget.DemoWidgetProvider;


public class MyForegroundService extends Service {
    private static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
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
//                    getBKCNGoldPrice();
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
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(newContent)
                .setSmallIcon(R.drawable.icon)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    private String getData() {
        // 模拟获取数据的操作
        return "Updated Data" + new Date().toString();
    }


    private void sendUpdateBroadcastToUpdateGoldPrice(GoldPrice goldPrice) {
        Intent intent = new Intent();
        intent.setAction("cn.tony.gold.price");
        intent.putExtra("content", String.format("买入价：%s, 卖出价：%s", goldPrice.getBid(), goldPrice.getSell()));
        sendBroadcast(intent);
        intent.setComponent(new ComponentName(this, DemoWidgetProvider.class));
        sendBroadcast(intent);
    }
}