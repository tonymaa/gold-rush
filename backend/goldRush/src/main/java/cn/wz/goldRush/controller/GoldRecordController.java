package cn.wz.goldRush.controller;

import cn.wz.goldRush.entity.GoldRecord;
import cn.wz.goldRush.service.GoldRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/gold-records")
@CrossOrigin(origins = "*")
public class GoldRecordController {

    @Autowired
    private GoldRecordService goldRecordService;

    @PostMapping
    public ResponseEntity<GoldRecord> createRecord(
            @RequestPart("record") GoldRecord record,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        return ResponseEntity.ok(goldRecordService.createRecord(record, photo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoldRecord> updateRecord(
            @PathVariable Long id,
            @RequestPart("record") GoldRecord record,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        return ResponseEntity.ok(goldRecordService.updateRecord(id, record, photo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        goldRecordService.deleteRecord(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoldRecord> getRecord(@PathVariable Long id) {
        return ResponseEntity.ok(goldRecordService.getRecord(id));
    }

    @GetMapping
    public ResponseEntity<List<GoldRecord>> getAllRecords() {
        return ResponseEntity.ok(goldRecordService.getAllRecords());
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<GoldRecord>> getRecordsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(goldRecordService.getRecordsByDateRange(startDate, endDate));
    }
} 