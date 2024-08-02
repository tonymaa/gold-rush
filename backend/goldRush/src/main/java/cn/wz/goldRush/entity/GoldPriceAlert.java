package cn.wz.goldRush.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_tbl")
public class GoldPriceAlert extends BaseEntity {
    @Column(name = "expected_profit")
    Double expectedProfit;
    @Column(name = "expected_threshold")
    Double expectedThreshold;
    @Column(name = "user_id")
    String userId;
}
