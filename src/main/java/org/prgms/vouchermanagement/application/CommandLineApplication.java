package org.prgms.vouchermanagement.application;

import org.prgms.vouchermanagement.io.Console;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class CommandLineApplication implements CommandLineRunner {

    private final Console console;

    public CommandLineApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            CommandMenu currentCommand;

            console.printCommandMenu();
            try {
                currentCommand = CommandMenu.getCommandMenu(console.getCommand());
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
            break;
        }
    }
}
