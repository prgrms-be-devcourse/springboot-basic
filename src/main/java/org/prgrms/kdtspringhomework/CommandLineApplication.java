package org.prgrms.kdtspringhomework;

import org.prgrms.kdtspringhomework.command.CommandLine;
import org.prgrms.kdtspringhomework.config.AppConfiguration;
import org.prgrms.kdtspringhomework.io.InputConsole;
import org.prgrms.kdtspringhomework.io.OutputConsole;
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
