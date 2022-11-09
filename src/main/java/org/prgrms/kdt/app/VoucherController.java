package org.prgrms.kdt.app;

import org.prgrms.kdt.command.CommandExecutor;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.VoucherMetaData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;

@Controller
public class VoucherController implements CommandLineRunner {

    private final Console console;
    private final CommandExecutor commandExecutor;

    public VoucherController(Console console, CommandExecutor commandExecutor) {
        this.console = console;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run(String... args) {
        VoucherControllerStatus voucherControllerStatus = new VoucherControllerStatus();
        while (voucherControllerStatus.isRunning()) {
            String userInput = console.getCommand();
            try {
                runCommand(userInput, voucherControllerStatus);
            } catch (RuntimeException exception) {
                console.printError(exception.getMessage());
            }
        }
    }

    private void runCommand(String userInput, VoucherControllerStatus voucherControllerStatus) {
        switch (CommandType.findCommandType(userInput)) {
            case CREATE -> commandExecutor.create(
                    new VoucherMetaData(
                            console.getType(),
                            console.getVoucherAmount()
                    )
            );
            case LIST -> console.printVouchers(commandExecutor.list());
            case EXIT -> voucherControllerStatus.quitProgram();
            default -> throw new InvalidParameterException();
        }
    }
}
