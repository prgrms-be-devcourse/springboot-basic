package com.programmers.assignment.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }
}
