package org.promgrammers.springbootbasic;

import org.promgrammers.springbootbasic.controller.CommandLineController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagementApplication implements CommandLineRunner {

    private final CommandLineController controller;

    public VoucherManagementApplication(CommandLineController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        controller.run();
    }
}