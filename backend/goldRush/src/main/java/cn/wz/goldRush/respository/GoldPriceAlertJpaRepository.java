package cn.wz.goldRush.respository;


import cn.wz.goldRush.entity.GoldPriceAlert;
import cn.wz.goldRush.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldPriceAlertJpaRepository extends JpaRepository<GoldPriceAlert, String> {

    
}
