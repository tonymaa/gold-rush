package cn.wz.goldRush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GoldRushApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldRushApplication.class, args);
	}

}
