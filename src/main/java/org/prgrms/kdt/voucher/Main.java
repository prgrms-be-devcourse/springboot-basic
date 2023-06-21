package org.prgrms.kdt.voucher;

import org.prgrms.kdt.common.config.AppConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(VoucherManagement.class).run();
    }
}
