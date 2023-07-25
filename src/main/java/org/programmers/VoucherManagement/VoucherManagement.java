package org.programmers.VoucherManagement;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class VoucherManagement {
    public static void main(String[] args) {
        SpringApplication.run(VoucherManagement.class, args);
    }
}
