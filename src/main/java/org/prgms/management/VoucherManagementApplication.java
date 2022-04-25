package org.prgms.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class VoucherManagementApplication {
    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(
                VoucherManagementApplication.class, args);
        var consoleApplication = applicationContext.getBean(ConsoleApplication.class);
        consoleApplication.run();
        applicationContext.close();
    }
}
