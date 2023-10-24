package com.prgrms.vouchermanager;

import com.prgrms.vouchermanager.domain.CommandHandler;
import com.prgrms.vouchermanager.exception.*;
import com.prgrms.vouchermanager.io.Command;
import com.prgrms.vouchermanager.io.Program;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class VoucherManagerApplication implements CommandLineRunner {

    private final CommandHandler commandHandler;

    @Autowired
    public VoucherManagerApplication(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            while(commandHandler.selectProgram()){}
        } catch (MyException e) {
            log.error(e.getMessage());
            System.out.println(e.consoleMessage());
        }
    }
}
