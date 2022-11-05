package org.prgrms.kdt;

import org.prgrms.kdt.io.ConsoleIO;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.utils.VoucherControllerManager;
import org.prgrms.kdt.voucher.VoucherInfo;
import org.prgrms.kdt.command.CommandExecutor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;

@Controller
public class VoucherController implements CommandLineRunner {

    private final ConsoleIO consoleIO;
    private final VoucherControllerManager voucherControllerManager;
    private final CommandExecutor commandExecutor;

    public VoucherController(ConsoleIO consoleIO, VoucherControllerManager voucherControllerManager, CommandExecutor commandExecutor) {
        this.consoleIO = consoleIO;
        this.voucherControllerManager = voucherControllerManager;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run(String... args) {
        while (voucherControllerManager.isRunning()) {
            String command = consoleIO.getCommand();

            try {
                runCommand(command);
            } catch (InvalidParameterException exception) {
                consoleIO.printCommandError();
            }
        }
    }

    private void runCommand(String userInput) {
        switch (CommandType.findCommandType(userInput)) {
            case CREATE -> {
                consoleIO.getVoucherType();
                consoleIO.getVoucherAmount();

                commandExecutor.createVoucher(new VoucherInfo());
            }
            case LIST -> {
            }
            case EXIT -> voucherControllerManager.quitProgram();
            default -> throw new InvalidParameterException();
        }
    }
}
