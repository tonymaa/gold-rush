package cn.wz.goldRush.respository;


import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldPriceJpaRepository extends JpaRepository<GoldPrice, String> {

    
}
