package cn.wz.goldRush.controller;

import cn.wz.goldRush.service.GoldService;
import cn.wz.goldRush.utils.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/gold")
public class GoldController {
    @Autowired
    GoldService goldService;

    @GetMapping("price")
    public ResponseInfo price(){
        goldService.getCnBankGoldPrice();
        return ResponseInfo.success("1000");
    }
}
