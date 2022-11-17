package com.programmers.assignment.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);


    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherApplication.class, args);

        logger.info("application start");
        applicationContext.getBean(CliApplication.class).runMenu();
        logger.info("application end");
    }
}
