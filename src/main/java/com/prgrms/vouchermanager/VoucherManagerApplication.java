package com.prgrms.vouchermanager;

import com.prgrms.vouchermanager.controller.VoucherController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagerApplication implements CommandLineRunner {

    private final VoucherController controller;

    @Autowired
    public VoucherManagerApplication(VoucherController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.run();
    }
}
