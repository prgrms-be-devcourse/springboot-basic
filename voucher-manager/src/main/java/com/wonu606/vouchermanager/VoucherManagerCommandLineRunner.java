package com.wonu606.vouchermanager;

import com.wonu606.vouchermanager.consoleInterface.CustomerConsoleInterface;
import com.wonu606.vouchermanager.consoleInterface.FrontConsoleInterface;
import com.wonu606.vouchermanager.consoleInterface.VoucherConsoleInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagerCommandLineRunner implements CommandLineRunner {
    private final FrontConsoleInterface frontConsoleInterface;

    public VoucherManagerCommandLineRunner(FrontConsoleInterface frontConsoleInterface) {
        this.frontConsoleInterface = frontConsoleInterface;
    }

    @Override
    public void run(String... args) {
        frontConsoleInterface.run();
    }
}
