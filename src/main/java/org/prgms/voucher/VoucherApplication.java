package org.prgms.voucher;

import org.prgms.voucher.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
        applicationContext.getBean(VoucherController.class).run();
    }

}
