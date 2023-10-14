package com.prgrms.vouchermanager;

import com.prgrms.vouchermanager.controller.VoucherController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class VoucherManagerApplication implements CommandLineRunner {

    private final VoucherController controller;

    public VoucherManagerApplication(VoucherController controller) {
        this.controller = controller;
    }

    @Autowired

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.run();
    }
}
