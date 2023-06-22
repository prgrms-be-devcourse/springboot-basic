package com.programmers.voucher;

import com.programmers.voucher.controller.CommandLineApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
        applicationContext.getBean(CommandLineApplication.class).run();
    }

}
