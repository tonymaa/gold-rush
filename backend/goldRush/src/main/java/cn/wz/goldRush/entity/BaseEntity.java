package cn.wz.goldRush.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {
    final static String COL_ID = "id";


    @Id
    @Column(name = COL_ID, length=18)
    private String id;
}
