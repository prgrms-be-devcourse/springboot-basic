package com.prgms.VoucherApp;

import com.prgms.VoucherApp.controller.VoucherApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherAppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(VoucherAppApplication.class, args);
        ac.getBean(VoucherApp.class).run();
    }

}
