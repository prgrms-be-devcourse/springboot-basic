package com.example.voucher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");

        //TODO: 사용자 입력 및 명령어 처리 로직 구현
    }
}
