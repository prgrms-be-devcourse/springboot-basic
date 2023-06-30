package org.prgrms.assignment.voucher;

import org.prgrms.assignment.voucher.controller.VoucherController;
import org.prgrms.kdt.KdtApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile({"local"})
public class VoucherApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherApp.class, args);
//        applicationContext.getBean(MyController.class).run();
//        applicationContext.register(VoucherAppConfiguration.class);
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        environment.setActiveProfiles("local");
//        applicationContext.refresh();
        applicationContext.getBean(VoucherController.class).run();
    }
}
