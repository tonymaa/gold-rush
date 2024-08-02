package cn.wz.goldRush.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class GoldPrice {
    Date date;
    Double bid;
    Double sell;
}
