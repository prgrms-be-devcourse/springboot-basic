package com.programmers.vouchermanagement;

import com.programmers.vouchermanagement.member.presentation.MemberController;
import com.programmers.vouchermanagement.utils.Command;
import com.programmers.vouchermanagement.utils.CommandNotFoundException;
import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.presentation.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagementController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagementController.class);

    private final ConsoleInputManager consoleInputManager;
    private final ConsoleOutputManager consoleOutputManager;
    private final VoucherController voucherController;
    private final MemberController memberController;

    public VoucherManagementController(ConsoleInputManager consoleInputManager, ConsoleOutputManager consoleOutputManager, VoucherController voucherController, MemberController memberController) {
        this.consoleInputManager = consoleInputManager;
        this.consoleOutputManager = consoleOutputManager;
        this.voucherController = voucherController;
        this.memberController = memberController;
    }

    @Override
    public void run(String... args) {

        logger.info("Start Voucher Program.");

        boolean chooseExit = false;

        while (!chooseExit) {

            consoleOutputManager.printCommandMenu();

            Command command;
            String input = consoleInputManager.inputString().toLowerCase();

            try {
                command = Command.getCommandByName(input);

            } catch (CommandNotFoundException e) {
                logger.error(e.getMessage() + "Console Input : " + input);

                consoleOutputManager.printEnterAgain(e.getMessage());
                continue;
            }

            switch (command) {

                case CREATE -> voucherController.createVoucher();

                case LIST -> voucherController.readAllVoucher();

                case BLACKLIST -> memberController.readAllBlackList();

                case EXIT -> {
                    consoleOutputManager.printExit();
                    chooseExit = true;
                }
            }
        }

        logger.info("Exit Voucher Program.");
    }
}
