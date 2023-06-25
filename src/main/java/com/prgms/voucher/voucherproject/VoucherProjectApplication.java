package com.prgms.voucher.voucherproject;

import com.prgms.voucher.voucherproject.io.Console;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherProjectApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(VoucherProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
