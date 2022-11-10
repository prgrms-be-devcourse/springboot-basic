package com.program.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class VoucherProgramApplication  {

    static final Logger logger = LoggerFactory.getLogger(VoucherProgramApplication.class);

    private final VoucherController voucherController;

    public VoucherProgramApplication(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    public static void main(String[] args) {

        logger.info("프로그램 시작");
        SpringApplication.run(VoucherProgramApplication.class, args);
        logger.info("프로그램 끝");
    }

    @PostConstruct
    public void start() {
        voucherController.run();
    }

}
