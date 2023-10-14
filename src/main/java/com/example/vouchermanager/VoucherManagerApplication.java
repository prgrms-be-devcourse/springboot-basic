package com.example.vouchermanager;

import com.example.vouchermanager.console.Command;
import com.example.vouchermanager.console.ConsolePrint;
import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.domain.VoucherInfo;
import com.example.vouchermanager.exception.NotCorrectCommand;
import com.example.vouchermanager.message.ConsoleMessage;
import com.example.vouchermanager.service.VoucherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class VoucherManagerApplication implements CommandLineRunner {
    private final ConsolePrint consolePrint;
    private final VoucherService service;

    public VoucherManagerApplication(ConsolePrint consolePrint, VoucherService service) {
        this.consolePrint = consolePrint;
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            Command command = consolePrint.run();

            if(command == Command.CREATE) {
                VoucherInfo voucherInfo = consolePrint.getVoucherInfo();
                service.create(voucherInfo);
                System.out.println(ConsoleMessage.COMPLETE_CREATE_VOUCHER.getMessage());
            } else if(command == Command.LIST) {
                consolePrint.printList(service.list());
            }
        } catch (NotCorrectCommand e) {
            System.out.println(ConsoleMessage.NOT_CORRECT_COMMAND.getMessage());
            System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());
            System.exit(0);
        }
    }
}
