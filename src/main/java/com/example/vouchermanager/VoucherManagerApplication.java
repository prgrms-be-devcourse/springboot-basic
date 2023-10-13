package com.example.vouchermanager;

import com.example.vouchermanager.console.Command;
import com.example.vouchermanager.console.CommandHandler;
import com.example.vouchermanager.console.VoucherType;
import com.example.vouchermanager.domain.VoucherInfo;
import com.example.vouchermanager.exception.NotCorrectCommand;
import com.example.vouchermanager.message.ConsoleMessage;
import com.example.vouchermanager.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;
import java.util.logging.ConsoleHandler;

@SpringBootApplication
public class VoucherManagerApplication implements CommandLineRunner {
    private final CommandHandler commandHandler;
    private final VoucherService service;

    public VoucherManagerApplication(CommandHandler commandHandler, VoucherService service) {
        this.commandHandler = commandHandler;
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            Command command = commandHandler.run();

            if(command == Command.CREATE) {
                VoucherInfo voucherInfo = commandHandler.getVoucherInfo();
                service.create(voucherInfo);
                System.out.println(ConsoleMessage.COMPLETE_CREATE_VOUCHER.getMessage());
            } else if(command == Command.LIST) {
                service.list();
            }
        } catch (NotCorrectCommand e) {
            System.out.println(ConsoleMessage.NOT_CORRECT_COMMAND.getMessage());
            System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());
            System.exit(0);
        }
    }
}
