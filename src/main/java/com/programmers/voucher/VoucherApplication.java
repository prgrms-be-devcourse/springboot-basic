package com.programmers.voucher;

import com.programmers.voucher.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args)
                .getBean(VoucherController.class)
                .run();
    }
}
