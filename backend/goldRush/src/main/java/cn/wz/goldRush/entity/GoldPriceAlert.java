package cn.wz.goldRush.entity;

import jakarta.persistence.*;
import lombok.Data;

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
/*    @Column(name = "user_name")
    String userName;*/
    @Column(name = "type")
    String type;
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName="name")
    private User user;
}
