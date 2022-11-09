package org.prgrms.kdt.app;

import org.prgrms.kdt.command.CommandExecutor;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.io.ConsoleIO;
import org.prgrms.kdt.voucher.VoucherMetaData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;

@Controller
public class VoucherController implements CommandLineRunner {

    private final ConsoleIO consoleIO;
    private final CommandExecutor commandExecutor;

    public VoucherController(ConsoleIO consoleIO, CommandExecutor commandExecutor) {
        this.consoleIO = consoleIO;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run(String... args) {
        VoucherControllerStatus voucherControllerStatus = new VoucherControllerStatus();
        while (voucherControllerStatus.isRunning()) {
            String userInput = consoleIO.getCommand();
            try {
                runCommand(userInput, voucherControllerStatus);
            } catch (RuntimeException exception) {
                consoleIO.printError(exception.getMessage());
            }
        }
    }

    private void runCommand(String userInput, VoucherControllerStatus voucherControllerStatus) {
        switch (CommandType.findCommandType(userInput)) {
            case CREATE -> commandExecutor.create(
                    new VoucherMetaData(
                            consoleIO.getType(),
                            consoleIO.getVoucherAmount()
                    )
            );
            case LIST -> consoleIO.printVouchers(commandExecutor.list());
            case EXIT -> voucherControllerStatus.quitProgram();
            default -> throw new InvalidParameterException();
        }
    }
}
