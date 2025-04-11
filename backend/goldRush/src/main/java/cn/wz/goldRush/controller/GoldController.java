package cn.wz.goldRush.controller;

import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.respository.UserJpaRepository;
import cn.wz.goldRush.service.EmailService;
import cn.wz.goldRush.service.GoldService;
import cn.wz.goldRush.utils.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/gold")
public class GoldController {
    @Autowired
    GoldService goldService;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    EmailService emailService;

    @GetMapping("/price")
    public ResponseInfo price(){
        GoldPrice goldPrice = goldService.getCnBankGoldPrice();
        return ResponseInfo.success(goldPrice);
    }
}
