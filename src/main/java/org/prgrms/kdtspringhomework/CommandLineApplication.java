package org.prgrms.kdtspringhomework;

import org.prgrms.kdtspringhomework.command.CommandLine;
import org.prgrms.kdtspringhomework.config.AppConfiguration;
import org.prgrms.kdtspringhomework.io.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Console console = new Console();

        new CommandLine(console, applicationContext).run();
    }
}
