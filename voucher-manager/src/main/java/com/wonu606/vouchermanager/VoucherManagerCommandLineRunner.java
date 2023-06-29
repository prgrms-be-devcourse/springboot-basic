package com.wonu606.vouchermanager;

import com.wonu606.vouchermanager.controller.VoucherController;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherManagerCommandLineRunner implements CommandLineRunner {

    private final VoucherController controller;

    @Override
    public void run(String... args) throws Exception {
        controller.run();
    }
}
