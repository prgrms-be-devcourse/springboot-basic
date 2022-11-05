package org.prgrms.kdt;

import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.model.CommandType;
import org.prgrms.kdt.utils.VoucherControllerManager;
import org.prgrms.kdt.voucher.VoucherMetaData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;

@Controller
public class VoucherController implements CommandLineRunner {

    private final Console console;
    private final VoucherControllerManager voucherManager;
    private final CommandExecutor commandExecutor;

    public VoucherController(Console console, VoucherControllerManager voucherManager, CommandExecutor commandExecutor) {
        this.console = console;
        this.voucherManager = voucherManager;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run(String... args) {
        while (voucherManager.isRunning()) {
            String userInput = console.getCommand();
            try {
                runCommand(userInput);
            } catch (InvalidParameterException exception) {
                console.printCommandError();
            } catch (NumberFormatException exception) {
                console.printVoucherAmountNumericError();
            } catch (IllegalArgumentException exception) {
                console.printVoucherTypeError();
            } catch (IllegalStateException exception) {
                console.printVoucherAmountOutOfBoundError();
            }
        }
    }

    private void runCommand(String userInput) {
        switch (CommandType.findCommandType(userInput)) {
            case CREATE -> commandExecutor.create(
                    new VoucherMetaData(
                            console.getType(),
                            console.getVoucherAmount()
                    )
            );
            case LIST -> {
            }
            case EXIT -> voucherManager.quitProgram();
            default -> throw new InvalidParameterException();
        }
    }
}
