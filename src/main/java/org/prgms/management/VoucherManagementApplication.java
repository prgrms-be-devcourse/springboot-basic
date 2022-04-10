package org.prgms.management;

import org.prgms.management.service.ConsoleService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class VoucherManagementApplication {
    public static void main(String[] args) {
        new ConsoleService().run();
    }
}
