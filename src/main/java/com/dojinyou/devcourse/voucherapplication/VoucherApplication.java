package com.dojinyou.devcourse.voucherapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
        ApplicationController applicationController = applicationContext.getBean(ApplicationController.class);
        applicationController.run();
    }
}
