package org.prgrms.deukyun.voucherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 바우처 앱 어플리케이션 - 바우처 애플리케이션의 스프링 껍데기
 */
@SpringBootApplication
public class VoucherAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherAppApplication.class, args);
    }
}
