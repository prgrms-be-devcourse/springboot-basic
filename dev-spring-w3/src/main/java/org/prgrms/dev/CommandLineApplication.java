package org.prgrms.dev;


import org.prgrms.dev.config.AppConfiguration;
import org.prgrms.dev.io.InputConsole;
import org.prgrms.dev.io.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        InputConsole inputConsole = new InputConsole();
        OutputConsole outputConsole = new OutputConsole();
        new CommandLine(inputConsole, outputConsole, applicationContext).run();
    }
}
