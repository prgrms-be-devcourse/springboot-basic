package org.prgms.voucherProgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringVoucherApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringVoucherApplication.class, args);
        applicationContext.getBean(VoucherProgram.class).run();
    }
}
