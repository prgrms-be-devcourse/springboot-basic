package org.prgrms.dev;


import org.prgrms.dev.config.AppConfiguration;
import org.prgrms.dev.io.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Console console = new Console();
        new CommandLine(console, console, applicationContext).run();
    }
}
