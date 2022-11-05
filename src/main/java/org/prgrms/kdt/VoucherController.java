package org.prgrms.kdt;

import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.model.CommandType;
import org.prgrms.kdt.utils.VoucherControllerManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;

@Controller
public class VoucherController implements CommandLineRunner {

    private final Console console;
    private final VoucherControllerManager voucherManager;

    public VoucherController(Console console, VoucherControllerManager voucherManager) {
        this.console = console;
        this.voucherManager = voucherManager;
    }

    @Override
    public void run(String... args) {
        while (voucherManager.isRunning()) {
            String userInput = console.getCommand();
            try {
                runCommand(userInput);
            } catch (InvalidParameterException exception) {
                console.printCommandError();
            }
        }
    }

    private void runCommand(String userInput) {
        switch (CommandType.findCommandType(userInput)) {
            case CREATE -> {
            }
            case LIST -> {
            }
            case EXIT -> voucherManager.quitProgram();
            default -> throw new InvalidParameterException();
        }
    }
}
