package org.prgrms.assignment.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {"org.prgrms.assignment.voucher.configuration"})
//@Profile({"dev"})
public class VoucherApp implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(VoucherApp.class, args);
    }

}
