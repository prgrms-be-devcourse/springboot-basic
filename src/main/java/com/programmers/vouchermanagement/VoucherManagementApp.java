package com.programmers.vouchermanagement;

import com.programmers.vouchermanagement.config.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class VoucherManagementApp {
    public static void main(String[] args) {
        SpringApplication.run(VoucherManagementApp.class, args);
    }
}