package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        InputConsole inputConsole = new InputConsole();
        OutputConsole outputConsole = new OutputConsole();
        new CommandLine(applicationContext, inputConsole, outputConsole).run();
    }
}
