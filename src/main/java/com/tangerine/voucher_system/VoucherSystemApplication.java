package com.tangerine.voucher_system;

import com.tangerine.voucher_system.application.ConsoleApplication;
import com.tangerine.voucher_system.application.global.config.JasyptConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class VoucherSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherSystemApplication.class, args)
                .getBean(ConsoleApplication.class)
                .run();
    }

}
