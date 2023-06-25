package com.prgmrs.voucher;

import com.prgmrs.voucher.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements CommandLineRunner {

    private final ConsoleService consoleService;

    @Autowired
    public VoucherApplication(ConsoleService console) {
        this.consoleService = console;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        consoleService.run();
    }
}
