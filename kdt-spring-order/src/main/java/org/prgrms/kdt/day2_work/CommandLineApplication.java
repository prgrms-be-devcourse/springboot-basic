package org.prgrms.kdt.day2_work;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.controller.CommandLineController;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        var appConfiguration = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = appConfiguration.getBean(VoucherService.class);
        new CommandLineController(voucherService).startProgram();
    }
}
