package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CommendLineApplication {
//    private static final Logger logger = LoggerFactory.getLogger(CommendLineApplication.class);
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CommendLineApplication.class, args);
        CommendLineRunner bean = applicationContext.getBean(CommendLineRunner.class);
        bean.run();
    }

}
