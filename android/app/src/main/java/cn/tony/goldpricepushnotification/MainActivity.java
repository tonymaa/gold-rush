package cn.tony.goldpricepushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import cn.tony.goldpricepushnotification.service.MyForegroundService;


public class MainActivity extends AppCompatActivity {
    BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setShowWhenLocked(true);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.gold_sell_price);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String intentAction = intent.getAction();
                if ("cn.tony.gold.price".equals(intentAction)) {
                    String content = intent.getStringExtra("content");
                    // 处理接收到的内容
                    Log.d("BroadcastReceiver", "Received content: " + content);
                    textView.setText(content);
                }
            }
        };

        Intent serviceIntent = new Intent(this, MyForegroundService.class);
        startService(serviceIntent);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.tony.gold.price");
        registerReceiver(receiver, intentFilter, Context.RECEIVER_EXPORTED);

        Button btn = (Button) findViewById(R.id.test_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("cn.tony.gold.price");
                intent.putExtra("content", "hello");
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}