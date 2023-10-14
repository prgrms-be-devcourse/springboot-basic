package com.example.vouchermanager;

import com.example.vouchermanager.console.Command;
import com.example.vouchermanager.console.ConsolePrint;
import com.example.vouchermanager.console.VoucherType;
import com.example.vouchermanager.controller.VoucherController;
import com.example.vouchermanager.exception.NotCorrectForm;
import com.example.vouchermanager.exception.NotCorrectScope;
import com.example.vouchermanager.message.ConsoleMessage;
import com.example.vouchermanager.message.LogMessage;
import com.example.vouchermanager.service.VoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
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
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.run();
    }
}
