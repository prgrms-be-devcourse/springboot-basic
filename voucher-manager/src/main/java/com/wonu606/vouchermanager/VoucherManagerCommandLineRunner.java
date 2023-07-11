package com.wonu606.vouchermanager;

import com.wonu606.vouchermanager.consoleInterface.VoucherConsoleInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagerCommandLineRunner implements CommandLineRunner {

    private final VoucherConsoleInterface consoleInterface;

    public VoucherManagerCommandLineRunner(VoucherConsoleInterface consoleInterface) {
        this.consoleInterface = consoleInterface;
    }

    @Override
    public void run(String... args) {
        consoleInterface.run();
    }
}
