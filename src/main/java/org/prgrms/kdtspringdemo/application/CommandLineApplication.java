package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.console.CommandOperator;
import org.prgrms.kdtspringdemo.console.Console;
import org.prgrms.kdtspringdemo.console.CustomerOperator;
import org.prgrms.kdtspringdemo.console.VoucherOperator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandLineApplication implements ApplicationRunner {
    private final CommandOperator voucherOperator;

    public CommandLineApplication(VoucherOperator voucherOperator) {
        this.voucherOperator = voucherOperator;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new ConsoleApp(new Console(), voucherOperator, new CustomerOperator());
    }
}