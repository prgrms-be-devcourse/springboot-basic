package com.devcourse.voucherapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherappApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherappApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(VoucherappApplication.class, args);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
