package org.devcourse.voucher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"org.devcourse.voucher.voucher", "org.devcourse.voucher.customer",
//        "org.devcourse.voucher.error", "org.devcourse.voucher.view", "org.devcourse.voucher.configuration", "org.devcourse.voucher"})
public class VoucherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(VoucherApplication.class, args);
        ac.getBean(ConsoleVoucherManager.class).run();
    }

}
