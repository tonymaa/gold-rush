package cn.tony.goldpricepushnotification.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.impl.Migration_1_2;

import cn.tony.goldpricepushnotification.dao.GoldPriceAlertDao;
import cn.tony.goldpricepushnotification.dao.GoldPriceDao;
import cn.tony.goldpricepushnotification.dao.UserDao;
import cn.tony.goldpricepushnotification.entity.GoldPrice;
import cn.tony.goldpricepushnotification.entity.GoldPriceAlert;
import cn.tony.goldpricepushnotification.entity.User;

@Database(entities = {
        GoldPrice.class, GoldPriceAlert.class, User.class
}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GoldPriceDao goldPriceDao();
    public abstract GoldPriceAlertDao goldPriceAlertDao();
    public abstract UserDao userDao();

    private static AppDatabase appDatabase = null;

    public static AppDatabase getInstance(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "gold_database")
                .allowMainThreadQueries()
//                .createFromAsset("init.db")
          /*          .addMigrations(new Migration(1, 2){

                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {

                        }
                    })*/
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

            User user = new User(1, "Tony", 0.0, 0.0);
            appDatabase.userDao().insertAll(user);
        }catch (Exception e){}
    }
}
