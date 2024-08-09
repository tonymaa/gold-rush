package cn.tony.goldpricepushnotification.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cn.tony.goldpricepushnotification.dao.GoldPriceAlertDao;
import cn.tony.goldpricepushnotification.dao.GoldPriceDao;
import cn.tony.goldpricepushnotification.entity.GoldPrice;
import cn.tony.goldpricepushnotification.entity.GoldPriceAlert;

@Database(entities = {GoldPrice.class, GoldPriceAlert.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GoldPriceDao goldPriceDao();
    public abstract GoldPriceAlertDao goldPriceAlertDao();

    private static AppDatabase appDatabase = null;

    public static AppDatabase getInstance(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "gold_database")
                .allowMainThreadQueries()
//                .createFromAsset("init.db")
                .build();
            initData();
        }
        return appDatabase;
    }

    private static void initData(){
        try {
            GoldPriceAlertDao goldPriceAlertDao = appDatabase.goldPriceAlertDao();
            GoldPriceAlert goldPriceAlert = new GoldPriceAlert(1, "Tony", "稳健", "active", 560.0, 560.0, "");
            goldPriceAlertDao.insertAll(goldPriceAlert);
        }catch (Exception e){}
    }
}
