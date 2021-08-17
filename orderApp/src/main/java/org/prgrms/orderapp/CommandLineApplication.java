package org.prgrms.orderapp;

import org.prgrms.orderapp.io.Console;
import org.prgrms.orderapp.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class CommandLineApplication {

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        new VoucherProgram(voucherService, new Console()).run();
    }
}
