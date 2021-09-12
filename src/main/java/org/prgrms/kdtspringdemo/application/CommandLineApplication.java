package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.console.VoucherOperator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandLineApplication implements ApplicationRunner {
    private final VoucherOperator voucherOperator;
    private final ConsoleApp consoleApp;

    public CommandLineApplication(VoucherOperator voucherOperator, ConsoleApp consoleApp) {
        this.voucherOperator = voucherOperator;
        this.consoleApp = consoleApp;
    }

    @Override
    public void run(ApplicationArguments args) {
        consoleApp.run();
    }
}