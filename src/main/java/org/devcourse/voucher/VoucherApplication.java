package org.devcourse.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(VoucherApplication.class, args);
        ac.getBean(ConsoleVoucherManager.class).run();
    }

}
