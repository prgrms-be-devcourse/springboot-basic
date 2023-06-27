package org.prgrms.kdt.voucher;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.controller.MyController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class VoucherApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("local");
        applicationContext.refresh();
        applicationContext.getBean(MyController.class).run();
    }
}
