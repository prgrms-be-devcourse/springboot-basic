package org.promgrammers.voucher;

import org.promgrammers.voucher.controller.VoucherController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements CommandLineRunner {

    private final VoucherController controller;

    public VoucherApplication(VoucherController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        controller.run();
    }
}
