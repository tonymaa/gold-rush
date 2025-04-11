package cn.wz.goldRush.service.impl;

import cn.wz.goldRush.entity.GoldRecord;
import cn.wz.goldRush.repository.GoldRecordRepository;
import cn.wz.goldRush.service.GoldRecordService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class GoldRecordServiceImpl implements GoldRecordService {

    @Autowired
    private GoldRecordRepository goldRecordRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public GoldRecord createRecord(GoldRecord record, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            String photoUrl = savePhoto(photo);
            record.setPhotoUrl(photoUrl);
        }
        return goldRecordRepository.save(record);
    }

    @Override
    public GoldRecord updateRecord(Long id, GoldRecord record, MultipartFile photo) {
        GoldRecord existingRecord = goldRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));

        if (photo != null && !photo.isEmpty()) {
            // Delete old photo if exists
            if (existingRecord.getPhotoUrl() != null) {
                deletePhoto(existingRecord.getPhotoUrl());
            }
            String photoUrl = savePhoto(photo);
            record.setPhotoUrl(photoUrl);
        } else {
            record.setPhotoUrl(existingRecord.getPhotoUrl());
        }

        record.setId(id);
        return goldRecordRepository.save(record);
    }

    @Override
    public void deleteRecord(Long id) {
        GoldRecord record = goldRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));
        
        if (record.getPhotoUrl() != null) {
            deletePhoto(record.getPhotoUrl());
        }
        
        goldRecordRepository.deleteById(id);
    }

    @Override
    public GoldRecord getRecord(Long id) {
        return goldRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));
    }

    @Override
    public List<GoldRecord> getAllRecords(Boolean isSummary) {
        return goldRecordRepository.findByIsSummaryOrderByPurchaseDateDesc(isSummary);
    }

    @Override
    public List<GoldRecord> getRecordsByDateRange(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDate.parse(startDate, formatter).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);
        return goldRecordRepository.findByPurchaseDateBetweenOrderByPurchaseDateDesc(start, end);
    }

    private String savePhoto(MultipartFile photo) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = UUID.randomUUID().toString() + getFileExtension(photo.getOriginalFilename());
            Path filePath = uploadPath.resolve(filename);
            Files.copy(photo.getInputStream(), filePath);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store photo", e);
        }
    }

    private void deletePhoto(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete photo", e);
        }
    }

    private String getFileExtension(String filename) {
        return filename != null && filename.contains(".") 
            ? filename.substring(filename.lastIndexOf("."))
            : "";
    }
} 