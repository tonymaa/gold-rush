package cn.tony.goldpricepushnotification.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cn.tony.goldpricepushnotification.entity.GoldPriceAlert;
import cn.tony.goldpricepushnotification.entity.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user where user.uid = :uid")
    User getById(int uid);

    @Insert
    void insertAll(User... goldPriceAlerts);

    @Delete
    void delete(User goldPriceAlert);

    @Query("UPDATE user SET gold_weight = :weight WHERE uid = :uid")
    void updateWeight(int uid, String weight);

    @Query("UPDATE user SET gold_capital = :capital WHERE uid = :uid")
    void updateCapital(int uid, String capital);
}
