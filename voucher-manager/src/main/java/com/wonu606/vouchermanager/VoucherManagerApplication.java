package com.wonu606.vouchermanager;

import com.wonu606.vouchermanager.Controller.VoucherController;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@AllArgsConstructor
public class VoucherManagerApplication implements CommandLineRunner {

    private final VoucherController controller;

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        controller.run();
    }
}
