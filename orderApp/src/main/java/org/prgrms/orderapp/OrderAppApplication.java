package org.prgrms.orderapp;

import org.prgrms.orderapp.io.Console;
import org.prgrms.orderapp.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OrderAppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderAppApplication.class, args);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        new VoucherProgram(voucherService, new Console()).run();
        applicationContext.close();
    }

}
