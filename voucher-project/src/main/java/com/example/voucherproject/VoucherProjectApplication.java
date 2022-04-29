package com.example.voucherproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class VoucherProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoucherProjectApplication.class, args);
    }
}
