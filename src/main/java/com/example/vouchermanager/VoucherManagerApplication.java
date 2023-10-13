package com.example.vouchermanager;

import com.example.vouchermanager.console.Command;
import com.example.vouchermanager.console.CommandHandler;
import com.example.vouchermanager.message.ConsoleMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(ConsoleMessage.SELECT_FUNCTION);
        Command command = CommandHandler.run();

        if(command == Command.CREATE) {
        } else if(command == Command.LIST) {
        } else if(command == Command.EXIT) {}
    }
}
