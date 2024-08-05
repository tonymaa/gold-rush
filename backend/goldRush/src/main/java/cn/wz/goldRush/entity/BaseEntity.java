package cn.wz.goldRush.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {
    final static String COL_ID = "id";


    @Id
    @Column(name = COL_ID, length=18)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
}
