package org.prgrms.voucherapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        var configurableApplicationContext = SpringApplication.run(VoucherApplication.class, args);

        VoucherManagement management = configurableApplicationContext.getBean(VoucherManagement.class);
        management.run();
    }

}
