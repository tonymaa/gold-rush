package cn.wz.goldRush.repository;

import cn.wz.goldRush.entity.GoldRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GoldRecordRepository extends JpaRepository<GoldRecord, Long> {
    List<GoldRecord> findByPurchaseDateBetweenOrderByPurchaseDateDesc(String start, String end);
} 