package cn.wz.goldRush.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gold_record")
public class GoldRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal weight; // 重量（克）

    @Column(nullable = false)
    private BigDecimal totalPrice; // 成交总价

    @Column(length = 100)
    private String purchaseChannel; // 购买渠道

    @Column(length = 500)
    private String remarks; // 备注

    @Column(nullable = false)
    private LocalDateTime purchaseDate; // 购买日期

    @Column(length = 200)
    private String photoUrl; // 照片URL

    @Column(nullable = false)
    private Boolean isSummary; // 是否是汇总模式（true为汇总模式，false为明细模式）

    @Column(nullable = false)
    private LocalDateTime createTime;

    @PrePersist
    public void prePersist() {
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
    }
} 