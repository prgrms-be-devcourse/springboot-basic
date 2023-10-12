package com.programmers.vouchermanagement;

import com.programmers.vouchermanagement.utils.Command;
import com.programmers.vouchermanagement.utils.CommandNotFoundException;
import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.presentation.VoucherController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagementController implements CommandLineRunner {

    private final ConsoleInputManager consoleInputManager;
    private final ConsoleOutputManager consoleOutputManager;
    private final VoucherController voucherController;

    public VoucherManagementController(ConsoleInputManager consoleInputManager, ConsoleOutputManager consoleOutputManager, VoucherController voucherController) {
        this.consoleInputManager = consoleInputManager;
        this.consoleOutputManager = consoleOutputManager;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {

        boolean chooseExit = false;

        while (!chooseExit) {

            consoleOutputManager.printCommandMenu();

            Command command = null;

            try {
                command = Command.getCommandByName(consoleInputManager.inputString());
            } catch (CommandNotFoundException e) {
                consoleOutputManager.printEnterAgain(e.getMessage());
            }

            switch (command) {

                case CREATE -> voucherController.createVoucher();

                case LIST -> voucherController.readAllVoucher();

                case EXIT -> {
                    consoleOutputManager.printExit();
                    chooseExit = true;
                }
            }
        }
    }
}
