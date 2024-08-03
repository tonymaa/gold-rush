package cn.wz.goldRsuh;

import cn.wz.goldRush.GoldRushApplication;
import cn.wz.goldRush.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = GoldRushApplication.class)
class GoldRsuhApplicationTests {

	@Autowired
	EmailService emailService;

	@Test
	void contextLoads() {
		emailService.sendSimpleEmail("1285242979@qq.com", "test title", "test content");
	}

}
