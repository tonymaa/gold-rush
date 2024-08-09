package cn.tony.goldpricepushnotification.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.tony.goldpricepushnotification.entity.GoldPriceAlert;

@Dao
public interface GoldPriceAlertDao {
    @Query("SELECT * FROM goldpricealert")
    List<GoldPriceAlert> getAll();

    @Query("SELECT * FROM goldpricealert where goldpricealert.uid = :uid")
    GoldPriceAlert getById(int uid);

    @Insert
    void insertAll(GoldPriceAlert... goldPriceAlerts);

    @Delete
    void delete(GoldPriceAlert goldPriceAlert);

    @Query("UPDATE goldpricealert SET left_point = :bid WHERE uid = :uid")
    void updateBid(int uid, String bid);

    @Query("UPDATE goldpricealert SET right_point = :sell WHERE uid = :uid")
    void updateSell(int uid, String sell);
}
