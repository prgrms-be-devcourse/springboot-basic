package com.programmers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class VoucherApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class);
        applicationContext.getBean(CommandLineApplication.class).run();
    }
}
