package cn.tony.goldpricepushnotification.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cn.tony.goldpricepushnotification.entity.GoldPrice;

@Dao
public interface GoldPriceDao {
    @Query("SELECT * FROM goldprice")
    List<GoldPrice> getAll();
/*
/*    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<GoldPrice> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    GoldPrice findByName(String first, String last);*/

    @Insert
    void insertAll(GoldPrice... goldPrices);

    @Delete
    void delete(GoldPrice goldPrices);
}
