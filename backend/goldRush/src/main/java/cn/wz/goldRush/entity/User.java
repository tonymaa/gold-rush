package cn.wz.goldRush.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "user_tbl")
public class User extends BaseEntity{
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "gold_weight")
    Double goldWeight;
    @Column(name = "gold_cost")
    Double goldCost;
}
