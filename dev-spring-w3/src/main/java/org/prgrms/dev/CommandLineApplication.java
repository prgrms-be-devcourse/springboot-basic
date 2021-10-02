package org.prgrms.dev;


import org.prgrms.dev.io.InputConsole;
import org.prgrms.dev.io.OutputConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CommandLineApplication.class, args);
        InputConsole inputConsole = new InputConsole();
        OutputConsole outputConsole = new OutputConsole();
        new CommandLine(inputConsole, outputConsole, applicationContext).run();
    }
}
