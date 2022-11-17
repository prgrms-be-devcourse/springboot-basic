package org.prgrms.kdt.app;

import org.prgrms.kdt.command.VoucherExecutor;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.customer.CustomerExecutor;
import org.prgrms.kdt.io.ConsoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;

@Controller
public class VoucherApp implements CommandLineRunner {

    private final ConsoleManager consoleManager;
    private final VoucherExecutor voucherExecutor;
    private final CustomerExecutor customerExecutor;
    private VoucherAppStatus voucherAppStatus;
    private static final Logger logger = LoggerFactory.getLogger(VoucherApp.class);

    public VoucherApp(ConsoleManager consoleManager, VoucherExecutor voucherExecutor, CustomerExecutor customerExecutor) {
        this.consoleManager = consoleManager;
        this.voucherExecutor = voucherExecutor;
        this.customerExecutor = customerExecutor;
    }

    @Override
    public void run(String... args) {
        voucherAppStatus = new VoucherAppStatus();
        while (voucherAppStatus.isRunning()) {
            String userInput = consoleManager.getCommand();
            try {
                runCommand(CommandType.of(userInput));
            } catch (RuntimeException exception) {
                logger.error(exception.getMessage(), exception);
                consoleManager.printError(exception.getMessage());
            }
        }
    }


    private void runCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> voucherExecutor.create(
                    consoleManager.getType(),
                    consoleManager.getVoucherAmount()
            );
            case LIST -> consoleManager.printVouchers(voucherExecutor.list());
            case BLACK -> consoleManager.printBlackList(customerExecutor.blacklist());
            case EXIT -> voucherAppStatus.quit();
            default -> throw new InvalidParameterException("Unknown command. CommandType: " + commandType.getCommand());
        }
    }
}
