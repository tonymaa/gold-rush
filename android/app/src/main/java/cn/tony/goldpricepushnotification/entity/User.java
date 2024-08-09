package cn.tony.goldpricepushnotification.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "gold_weight")
    public Double goldWeight;

    @ColumnInfo(name = "gold_capital")
    public Double goldCapital;


    public User(int uid, String userName, Double goldWeight, Double goldCapital) {
        this.uid = uid;
        this.userName = userName;
        this.goldWeight = goldWeight;
        this.goldCapital = goldCapital;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getGoldWeight() {
        return goldWeight;
    }

    public void setGoldWeight(Double goldWeight) {
        this.goldWeight = goldWeight;
    }

    public Double getGoldCapital() {
        return goldCapital;
    }

    public void setGoldCapital(Double goldCapital) {
        this.goldCapital = goldCapital;
    }
}
