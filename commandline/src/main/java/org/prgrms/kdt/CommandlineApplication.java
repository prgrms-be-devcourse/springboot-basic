package org.prgrms.kdt;

import org.prgrms.kdt.controller.CommandLineController;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CommandlineApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CommandlineApplication.class, args);
        CommandLineController controller = applicationContext.getBean(CommandLineController.class);
        controller.run();
    }
}
