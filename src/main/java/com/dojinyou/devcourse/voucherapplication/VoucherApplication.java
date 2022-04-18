package com.dojinyou.devcourse.voucherapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {
    private ApplicationController applicationController;

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

}
