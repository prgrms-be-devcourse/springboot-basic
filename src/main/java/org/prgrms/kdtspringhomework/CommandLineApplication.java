package org.prgrms.kdtspringhomework;

import org.prgrms.kdtspringhomework.command.CommandLine;
import org.prgrms.kdtspringhomework.config.AppConfiguration;
import org.prgrms.kdtspringhomework.io.InputConsole;
import org.prgrms.kdtspringhomework.io.OutputConsole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CommandLineApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CommandLineApplication.class, args);
        InputConsole inputConsole = new InputConsole();
        OutputConsole outputConsole = new OutputConsole();

        new CommandLine(inputConsole, outputConsole, applicationContext).run();
    }
}