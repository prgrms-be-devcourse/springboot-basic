package org.prgrms.kdt.app;

import org.prgrms.kdt.command.CommandExecutor;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.io.ConsoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;

@Controller
public class VoucherController implements CommandLineRunner {

    private final ConsoleManager consoleManager;
    private final CommandExecutor commandExecutor;
    private VoucherControllerStatus voucherControllerStatus;
    private static Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(ConsoleManager consoleManager, CommandExecutor commandExecutor) {
        this.consoleManager = consoleManager;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run(String... args) {
        voucherControllerStatus = new VoucherControllerStatus();
        while (voucherControllerStatus.isRunning()) {
            String userInput = consoleManager.getCommand();
            try {
                runCommand(CommandType.of(userInput));
            } catch (RuntimeException exception) {
                logger.warn(exception.getMessage(), exception);
                consoleManager.printError(exception.getMessage());
            }
        }
    }


    private void runCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> commandExecutor.create(
                    consoleManager.getType(),
                    consoleManager.getVoucherAmount()
            );
            case LIST -> consoleManager.printVouchers(commandExecutor.list());
            case EXIT -> voucherControllerStatus.quit();
            default -> throw new InvalidParameterException("Unknown command. CommandType: " + commandType.getCommand());
        }
    }
}
