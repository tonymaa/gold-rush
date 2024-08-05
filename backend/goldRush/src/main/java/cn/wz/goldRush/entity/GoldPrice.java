package cn.wz.goldRush.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Data
@Table(name = "gold_price_tbl")
public class GoldPrice extends BaseEntity{
    @Column(name = "update_date")
    Date updateDate;
    @Column(name = "bid")
    Double bid;
    @Column(name = "sell")
    Double sell;
}
