package com.tangerine.voucher_system;

import com.tangerine.voucher_system.application.ConsoleApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherSystemApplication.class, args)
                .getBean(ConsoleApplication.class)
                .run();
    }

}
