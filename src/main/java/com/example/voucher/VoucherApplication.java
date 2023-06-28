package com.example.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.voucher.ui.Output;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Output.printProgramInfo();
        } catch (Exception e) {
            logger.error("예외 발생: ", e);
        }
    }
}
