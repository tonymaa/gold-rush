package cn.wz.goldRush.respository;


import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoldPriceJpaRepository extends JpaRepository<GoldPrice, String> {
    @Query("SELECT g FROM GoldPrice g ORDER BY g.updateDate DESC limit 1")
    GoldPrice findTopByOrderByUpdateDate();
    
}
