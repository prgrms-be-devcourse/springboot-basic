package org.prgrms.kdt;

import org.prgrms.kdt.commendLine.ConsoleInput;
import org.prgrms.kdt.commendLine.ConsoleOutput;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.member.controller.MemberController;
import org.prgrms.kdt.util.ErrorMessage;
import org.prgrms.kdt.util.Menu;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CommendLineRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommendLineRunner.class);
    private final VoucherController voucherController;
    private final MemberController memberController;

    public CommendLineRunner(VoucherController voucherController, MemberController memberController) {
        this.voucherController = voucherController;
        this.memberController = memberController;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;
        while (isRunning) {
            try {
                ConsoleOutput.printMenu();
                String getUserMenu = ConsoleInput.getUserMenu();
                Menu menu = Menu.getMenu(getUserMenu);
                isRunning = executeAction(menu);

            } catch (InvalidInputException e) {
                ConsoleOutput.printError();
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }

    // handler mapping
    private boolean executeAction(Menu menu) {
        switch (menu) {
            case CREATE:
                voucherController.create();
                return true;
            case EXIT:
                return false;
            case LIST:
                voucherController.findAll();
                return true;
            case BLACK_LIST:
                memberController.findAllBlackMember();
                return true;
            default:
                throw new InvalidInputException(ErrorMessage.INVALID_INPUT);
        }
    }
}
