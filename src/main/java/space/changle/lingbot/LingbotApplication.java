package space.changle.lingbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("space.changle.lingbot.**.mapper")
@SpringBootApplication
public class LingbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(LingbotApplication.class, args);
    }

}
