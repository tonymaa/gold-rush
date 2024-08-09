package cn.tony.goldpricepushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;



import cn.tony.goldpricepushnotification.database.AppDatabase;
import cn.tony.goldpricepushnotification.databinding.ActivityMainBinding;
import cn.tony.goldpricepushnotification.entity.GoldPrice;
import cn.tony.goldpricepushnotification.entity.GoldPriceAlert;
import cn.tony.goldpricepushnotification.service.MyForegroundService;


public class MainActivity extends AppCompatActivity {
    final String MainActivityTag = "MainActivity";
    BroadcastReceiver receiver;
    ActivityMainBinding mainBinding;
    AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getInstance(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String intentAction = intent.getAction();
                if ("cn.tony.gold.price".equals(intentAction)) {
                    GoldPrice goldPrice = intent.getSerializableExtra("goldPrice", GoldPrice.class);
                    mainBinding.setGoldPrice(goldPrice);
                }
            }
        };

        Intent serviceIntent = new Intent(this, MyForegroundService.class);
        startService(serviceIntent);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.tony.gold.price");
        registerReceiver(receiver, intentFilter, Context.RECEIVER_EXPORTED);

        mainBinding.setGoldPriceAlert(appDatabase.goldPriceAlertDao().getById(1));
        EditText alertBid = (EditText) findViewById(R.id.gold_price_alert_bid);
        alertBid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                appDatabase.goldPriceAlertDao().updateBid(1, s.toString());
            }
        });
        EditText alertSell = (EditText) findViewById(R.id.gold_price_alert_sell);
        alertSell.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                appDatabase.goldPriceAlertDao().updateSell(1, s.toString());
            }
        });
    /*    Button btn = (Button) findViewById(R.id.test_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("cn.tony.gold.price");
                intent.putExtra("content", "hello");
                sendBroadcast(intent);
            }
        });*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}