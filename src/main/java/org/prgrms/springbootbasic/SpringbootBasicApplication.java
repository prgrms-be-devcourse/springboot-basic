package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.controller.VoucherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
        var voucherController = applicationContext.getBean(VoucherController.class);
        voucherController.run();
    }

}
