package com.wonu606.vouchermanager;

import com.wonu606.vouchermanager.consoleInterface.CustomerConsoleInterface;
import com.wonu606.vouchermanager.consoleInterface.VoucherConsoleInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagerCommandLineRunner implements CommandLineRunner {
    private final CustomerConsoleInterface customerConsoleInterface;

    public VoucherManagerCommandLineRunner(CustomerConsoleInterface customerConsoleInterface) {
        this.customerConsoleInterface = customerConsoleInterface;
    }

    @Override
    public void run(String... args) {
        customerConsoleInterface.run();
    }
}
