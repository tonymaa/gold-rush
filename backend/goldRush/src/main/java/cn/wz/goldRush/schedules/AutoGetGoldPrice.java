package cn.wz.goldRush.schedules;


import cn.wz.goldRush.entity.GoldPrice;
import cn.wz.goldRush.entity.GoldPriceAlert;
import cn.wz.goldRush.entity.User;
import cn.wz.goldRush.respository.GoldPriceAlertJpaRepository;
import cn.wz.goldRush.respository.GoldPriceJpaRepository;
import cn.wz.goldRush.respository.UserJpaRepository;
import cn.wz.goldRush.service.EmailService;
import cn.wz.goldRush.service.GoldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AutoGetGoldPrice {

    private final static Long DefaultNotifyIntervalS = 60L * 5;

    private final static String Active = "active";
    private final static String Inactive = "active";
    private  static Logger logger = LoggerFactory.getLogger(AutoGetGoldPrice.class);

    @Autowired
    GoldService goldService;

    @Autowired
    GoldPriceJpaRepository goldPriceJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    GoldPriceAlertJpaRepository goldPriceAlertJpaRepository;

    @Autowired
    EmailService emailService;

    @Scheduled(cron = "*/10 * * * * ?")
    public void start(){
        GoldPrice goldPrice = goldService.getCnBankGoldPrice();
        goldPriceJpaRepository.save(goldPrice);
        alertUser(goldPrice);
    }

    private void alertUser(GoldPrice goldPrice){
        GoldPriceAlert probe = new GoldPriceAlert();
        Example<GoldPriceAlert> example = Example.of(probe);
        Function<FluentQuery.FetchableFluentQuery<GoldPriceAlert>, List<GoldPriceAlert>> queryFunction =
                query -> query.all().stream()
                        .filter(alert -> {
                            try {
                                return Active.equals(alert.getStatus()) && (
                                    (goldPrice.getBid() != null && alert.getLeftPoint() > goldPrice.getBid()) ||
                                            (goldPrice.getSell() != null && alert.getRightPoint() < goldPrice.getSell())
                                    );
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        })
                        .distinct()
                        .collect(Collectors.toList());
        List<GoldPriceAlert> goldPriceAlerts = goldPriceAlertJpaRepository.findBy(example, queryFunction);
//        logger.info(goldPriceAlerts.toString());
        for (GoldPriceAlert goldPriceAlert : goldPriceAlerts) {
            if (goldPriceAlert.getNotifyIntervalS() == null) goldPriceAlert.setNotifyIntervalS(DefaultNotifyIntervalS);
            if (
                    goldPriceAlert.getterLastNotifyDate() != null &&
                    (new Date().getTime() - goldPriceAlert.getterLastNotifyDate().getTime()) / 1000 < goldPriceAlert.getNotifyIntervalS()
            ) continue;
            if (goldPrice.getBid() < goldPriceAlert.getLeftPoint()){
                User user = goldPriceAlert.getUser();
                String title = String.format("【黄金降价%s】清仓大甩卖，黄金降价啦，原本要580一克，现在仅需%s!!!", goldPrice.getBid(), goldPrice.getBid());
                String content = String.format("【%s】来自%s的策略【%s】",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                        user.getName(), goldPriceAlert.getDescription());
                emailService.sendSimpleEmail(user.getEmail(), title, content);
                goldPriceAlert.setterLastNotifyDate(new Date());
                goldPriceAlertJpaRepository.save(goldPriceAlert);
            }
            if (goldPrice.getSell() > goldPriceAlert.getRightPoint()){
                User user = goldPriceAlert.getUser();
                String title = String.format("【黄金升值】%s!!! %s!!! %s!!!", goldPrice.getSell(), goldPrice.getSell(), goldPrice.getSell());
                String content = String.format("【%s】来自%s的策略【%s】",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                        user.getName(), goldPriceAlert.getDescription());
                emailService.sendSimpleEmail(user.getEmail(), title, content);
                goldPriceAlert.setterLastNotifyDate(new Date());
                goldPriceAlertJpaRepository.save(goldPriceAlert);
            }
        }
    }


}
