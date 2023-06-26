package com.prgms.VoucherApp;

import com.prgms.VoucherApp.controller.VoucherApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherAppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(VoucherAppApplication.class, args);
        VoucherApp voucherApp = configurableApplicationContext.getBean(VoucherApp.class);
        voucherApp.run();
    }

}
