package prgms.vouchermanagementapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "prgms.vouchermanagementapp.configuration")
public class VoucherManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagementApp.class, args);
    }
}
