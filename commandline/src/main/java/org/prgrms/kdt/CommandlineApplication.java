package org.prgrms.kdt;

import org.prgrms.kdt.voucher.controller.CommandLineController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication()
public class CommandlineApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CommandlineApplication.class, args);
        CommandLineController controller = applicationContext.getBean(CommandLineController.class);
        controller.run();
    }
}