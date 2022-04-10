package com.prgrms.management;

import com.prgrms.management.command.CommandService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VoucherApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
        applicationContext.getBean(CommandService.class).run();
    }
}
