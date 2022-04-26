package org.prgrms.voucherapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(VoucherApplication.class, args);

        VoucherManagement management = configurableApplicationContext.getBean(VoucherManagement.class);
        management.run();
    }

}
