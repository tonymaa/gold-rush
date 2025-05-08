package cn.wz.goldRush.controller;

import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.respository.GoldPriceJpaRepository;
import cn.wz.goldRush.respository.UserJpaRepository;
import cn.wz.goldRush.service.EmailService;
import cn.wz.goldRush.service.GoldService;
import cn.wz.goldRush.utils.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "/api/gold")
public class GoldController {
    @Autowired
    GoldService goldService;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    EmailService emailService;
    @Autowired
    GoldPriceJpaRepository goldPriceJpaRepository;
    @GetMapping("/price")
    public ResponseInfo price(){
        GoldPrice goldPrice = goldService.getCnBankGoldPrice();
        return ResponseInfo.success(goldPrice);
    }

    @GetMapping("/today-prices")
    public ResponseInfo getTodayPrices() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<GoldPrice> todayPrices = goldPriceJpaRepository.findByUpdateDateBetweenOrderByUpdateDateDesc(today);
        return ResponseInfo.success(todayPrices);
    }
    
    
}
