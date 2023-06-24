package com.dev.voucherproject;


import com.dev.voucherproject.controller.console.ConsoleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherProjectApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(VoucherProjectApplication.class, args);
        ConsoleController consoleApp = ac.getBean(ConsoleController.class);
        consoleApp.run();
    }
}
