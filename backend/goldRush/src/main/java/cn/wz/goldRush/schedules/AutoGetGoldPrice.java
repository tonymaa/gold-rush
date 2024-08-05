package cn.wz.goldRush.schedules;


import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.respository.GoldPriceJpaRepository;
import cn.wz.goldRush.service.GoldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutoGetGoldPrice {
    private  static Logger logger = LoggerFactory.getLogger(AutoGetGoldPrice.class);

    @Autowired
    GoldService goldService;

    @Autowired
    GoldPriceJpaRepository goldPriceJpaRepository;

    @Scheduled(cron = "*/10 * * * * ?")
    public void start(){
        GoldPrice goldPrice = goldService.getCnBankGoldPrice();
        goldPriceJpaRepository.save(goldPrice);
    }


}
