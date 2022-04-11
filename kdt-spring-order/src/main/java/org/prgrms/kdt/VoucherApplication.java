package org.prgrms.kdt;

import org.prgrms.kdt.io.InputConsole;
import org.prgrms.kdt.io.OutputConsole;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(VoucherApplication.class, args);
        InputConsole inputConsole = new InputConsole();
        OutputConsole outputConsole = new OutputConsole();

        new CommandLine(applicationContext, inputConsole, outputConsole).run();
    }
}
