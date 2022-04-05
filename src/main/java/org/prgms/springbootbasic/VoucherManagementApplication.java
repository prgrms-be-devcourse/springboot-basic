package org.prgms.springbootbasic;

import org.prgms.springbootbasic.management.service.ConsoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagementApplication {

    public static void main(String[] args) {
        new ConsoleService().run();
    }
}
