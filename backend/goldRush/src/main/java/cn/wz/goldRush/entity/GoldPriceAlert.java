package cn.wz.goldRush.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "gold_price_alert_tbl")
public class GoldPriceAlert extends BaseEntity {
    @Column(name = "description")
    String description;
    @Column(name = "status")
    String status;
    @Column(name = "left_point")
    Double leftPoint;
    @Column(name = "right_point")
    Double rightPoint;
    @Column(name = "last_notify_date")
    String lastNotifyDate;
    @Column(name = "notify_interval_s")
    Long notifyIntervalS;
    @Column(name = "type")
    String type;
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName="name")
    private User user;

    public Date getterLastNotifyDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastNotifyDate);
        } catch (ParseException e) {
//            throw new RuntimeException(e);
        }
        return null;
    }

    public void setterLastNotifyDate(Date lastNotifyDate) {
        this.lastNotifyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastNotifyDate);
    }
}
