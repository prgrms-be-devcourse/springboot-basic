package com.dev.voucherproject;


import com.dev.voucherproject.controller.console.ConsoleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherProjectApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(VoucherProjectApplication.class, args);
        ConsoleController AppV1 = ac.getBean(ConsoleController.class);
        AppV1.run();
    }
}
