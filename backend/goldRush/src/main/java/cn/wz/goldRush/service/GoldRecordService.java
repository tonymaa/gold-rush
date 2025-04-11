package cn.wz.goldRush.service;

import cn.wz.goldRush.entity.GoldRecord;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface GoldRecordService {
    GoldRecord createRecord(GoldRecord record, MultipartFile photo);
    GoldRecord updateRecord(Long id, GoldRecord record, MultipartFile photo);
    void deleteRecord(Long id);
    GoldRecord getRecord(Long id);
    List<GoldRecord> getAllRecords(Boolean isSummary);
    List<GoldRecord> getRecordsByDateRange(String startDate, String endDate);
} 