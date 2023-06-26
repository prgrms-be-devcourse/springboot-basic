package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CommendLineApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CommendLineApplication.class, args);
        CommendLineRunner bean = applicationContext.getBean(CommendLineRunner.class);
        bean.run();
    }

}
