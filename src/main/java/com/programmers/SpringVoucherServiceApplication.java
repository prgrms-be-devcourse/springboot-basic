package com.programmers;

import com.programmers.voucher.CommandLineApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringVoucherServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringVoucherServiceApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(SpringVoucherServiceApplication.class, args);

        CommandLineApplication commandLineApplication = ac.getBean(CommandLineApplication.class);

        logger.info("애플리케이션 기동");
        commandLineApplication.run();

    }

}
