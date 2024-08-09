package cn.tony.goldpricepushnotification.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class GoldPrice implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    public int uid;
    @ColumnInfo(name = "update_date")
    String updateDate;
    @ColumnInfo(name = "bid")
    Double bid;
    @ColumnInfo(name = "sell")
    Double sell;
    Double goldEarnings;

    public Double getGoldEarnings() {
        return goldEarnings;
    }

    public void setGoldEarnings(Double goldEarnings) {
        this.goldEarnings = goldEarnings;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Date getterUpdateDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updateDate);
        } catch (ParseException e) {
//            throw new RuntimeException(e);
        }
        return null;
    }

    public void setterUpdateDate(Date updateDate) {
        this.updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateDate);
    }
}
