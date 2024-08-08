package cn.tony.goldpricepushnotification.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import cn.tony.goldpricepushnotification.R;
import cn.tony.goldpricepushnotification.service.MyForegroundService;

public class DemoWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "DemoWidgetProvider";

//    RemoteViews remoteViews = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String intentAction = intent.getAction();
        if ("cn.tony.gold.price".equals(intentAction)) {
            String content = intent.getStringExtra("content");
            // 处理接收到的内容
            Log.d("BroadcastReceiver", "Received content: " + content);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, DemoWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, content);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, "Initial Text");
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String text) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_demo_layout);
        views.setTextViewText(R.id.price_text, text);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

/*

    private static void updateWidgetClickEvent(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        DebugLog.log(TAG, "updateWidgetClickEvent");
        Intent intent = new Intent("com.demo.scan.main");
        intent.setPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("WIDGET_NAME", "ScanWidgetProvider");

        //remoteViews设置点击事件，只能使用PendingIntent
        intent.putExtra("CLICK_ACTION", "scan_demo_image_click");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, R.id.iv_scan, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_scan, pendingIntent);
        //渲染UI
        submitRender(appWidgetManager, appWidgetIds, remoteViews);
    }
    //渲染小组件
    private static void submitRender(AppWidgetManager appWidgetManager, int[] appWidgetIds, RemoteViews remoteViews) {
        for (int appWidgetId : appWidgetIds) {
            Log.d(TAG, "submitRender,widgetId:" + appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }



    public static Bitmap decodeSampleBitmap(Context context, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        //计算采样比例
        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);
        DebugLog.log(TAG,"options.inSampleSize:" + options.inSampleSize);
        options.inJustDecodeBounds = false;
        return bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
    }*/
}
