package org.prgrms.kdt.day2_work;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.controller.CommandLineController;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        var appConfiguration = new AnnotationConfigApplicationContext();
        appConfiguration.register(AppConfiguration.class);
        var environment = appConfiguration.getEnvironment();
        environment.setActiveProfiles("local");
        appConfiguration.refresh();
        File blackList = appConfiguration.getResource("customer_blacklist.csv").getFile();
        var voucherService = appConfiguration.getBean(VoucherService.class);
        new CommandLineController(voucherService).startProgram();
        System.out.println();
    }
}
