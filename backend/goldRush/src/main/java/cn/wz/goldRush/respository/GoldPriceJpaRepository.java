package cn.wz.goldRush.respository;
import java.time.LocalDateTime;
import java.util.List;
import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoldPriceJpaRepository extends JpaRepository<GoldPrice, String> {
    @Query("SELECT g FROM GoldPrice g ORDER BY g.updateDate DESC limit 1")
    GoldPrice findTopByOrderByUpdateDate();

    @Query(value = "SELECT * FROM gold_price_tbl WHERE DATE(update_date) = DATE(:date) ORDER BY update_date DESC", nativeQuery = true)
    List<GoldPrice> findByUpdateDateBetweenOrderByUpdateDateDesc(String date);
}
