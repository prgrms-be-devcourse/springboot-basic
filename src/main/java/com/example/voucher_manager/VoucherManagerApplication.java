package com.example.voucher_manager;

import com.example.voucher_manager.application.RunApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

}
