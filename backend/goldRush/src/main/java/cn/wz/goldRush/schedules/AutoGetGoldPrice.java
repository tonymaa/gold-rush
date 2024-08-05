package cn.wz.goldRush.schedules;


import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.respository.GoldPriceJpaRepository;
import cn.wz.goldRush.service.GoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutoGetGoldPrice {

    @Autowired
    GoldService goldService;

    @Autowired
    GoldPriceJpaRepository goldPriceJpaRepository;

    @Scheduled(cron = "0/10 * * * * ?")
    public void start(){
        GoldPrice goldPrice = goldService.getCnBankGoldPrice();
        goldPriceJpaRepository.save(goldPrice);
    }


}
