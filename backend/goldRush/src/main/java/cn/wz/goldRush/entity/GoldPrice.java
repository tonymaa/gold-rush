package cn.wz.goldRush.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "gold_price_tbl")
public class GoldPrice extends BaseEntity{
    @Column(name = "update_date")
    String updateDate;
    @Column(name = "bid")
    Double bid;
    @Column(name = "sell")
    Double sell;

    public Date getterUpdateDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updateDate);
        } catch (ParseException e) {
//            throw new RuntimeException(e);
        }
        return null;
    }

    public void setterUpdateDate(Date updateDate) {
        if (updateDate != null) {
            this.updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(updateDate);
        }
    }
}
