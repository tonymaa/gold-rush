package cn.tony.goldpricepushnotification.entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class GoldPriceAlert implements Serializable {
    @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "uid")
    public int uid;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "status")
    String status;
    @ColumnInfo(name = "left_point")
    Double leftPoint;
    @ColumnInfo(name = "right_point")
    Double rightPoint;
/*    @ColumnInfo(name = "last_notify_date")
    String lastNotifyDate;
    @ColumnInfo(name = "notify_interval_s")
    Long notifyIntervalS;
    @ColumnInfo(name = "type")*/
    String type;

    public GoldPriceAlert(int uid, String name, String description, String status, Double leftPoint, Double rightPoint, String type) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.status = status;
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(Double leftPoint) {
        this.leftPoint = leftPoint;
    }

    public Double getRightPoint() {
        return rightPoint;
    }

    public void setRightPoint(Double rightPoint) {
        this.rightPoint = rightPoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
