package cn.tony.goldpricepushnotification.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

public class DemoWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "DemoWidgetProvider";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {        Log.d(TAG, "onUpdate");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        if (appWidgetManager == null || appWidgetIds == null) {
            Log.d(TAG, "onUpdate error, appWidgetManager=" + appWidgetManager + " appWidgetIds=" + appWidgetIds);
            return;
        }
        //小组件是运行在独立进程中，所以涉及到跨进程的渲染，故只能使用remoteViews加载和设置ui
//        createRemoteViews(context);
//        updateDefaultWidget(context, appWidgetManager, appWidgetIds);
    }

/*    private static void updateDefaultWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int roundCorner = Utils.dip2px(context, 14);    //dp转化为px，工具类代码不贴了，网上很多
        int topWidth = Utils.dip2px(context, 80);
        int topHeight = Utils.dip2px(context, 80);
        //将图片压缩加载
        Bitmap topBitmap = decodeSampleBitmap(context, R.drawable.demo_scan, topWidth, topHeight);
        if (topBitmap!= null) {
            //使用remoteViews设置图片视图
            remoteViews.setImageViewBitmap(R.id.iv_scan, topBitmap);
        }
        //使用remoteViews设置展示文字和颜色
        remoteViews.setTextViewText(R.id.tv_scan, "AR扫一扫");
        remoteViews.setTextColor(R.id.tv_scan, Color.RED);
        updateWidgetClickEvent(context, appWidgetManager, appWidgetIds);
    }

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

    private void createRemoteViews(Context context) {
        if (remoteViews == null) {
            DebugLog.log(TAG, "createRemoteViews");
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.scan_widget_layout);
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
