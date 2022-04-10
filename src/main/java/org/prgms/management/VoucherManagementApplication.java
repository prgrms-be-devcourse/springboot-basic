package org.prgms.management;

import org.prgms.management.service.ConsoleService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class VoucherManagementApplication {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String command = sc.next();
        new ConsoleService().run(command);
    }
}
