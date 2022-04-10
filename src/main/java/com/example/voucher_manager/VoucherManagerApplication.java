package com.example.voucher_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VoucherManagerApplication.class, args);
        var application = context.getBean(RunApplication.class);
        application.run();
    }

}
