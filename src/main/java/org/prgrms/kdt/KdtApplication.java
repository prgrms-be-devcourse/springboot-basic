package org.prgrms.kdt;

import org.prgrms.kdt.commandapp.CommandLineApplication;
import org.prgrms.kdt.commandapp.Console;
import org.prgrms.kdt.commandapp.VoucherCommandOperator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KdtApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(KdtApplication.class, args);
        var voucherCommandOperator = applicationContext.getBean(VoucherCommandOperator.class);
        var console = new Console();
        new CommandLineApplication(console, console, voucherCommandOperator).run();

    }

}
