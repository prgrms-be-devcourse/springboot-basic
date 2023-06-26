package org.prgrms.kdt;

import org.prgrms.kdt.commendLine.ConsoleInput;
import org.prgrms.kdt.commendLine.ConsoleOutput;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.member.controller.MemberController;
import org.prgrms.kdt.util.Menu;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommendLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommendLineRunner.class);
    private final VoucherController voucherController;
    private final MemberController memberController;

    public CommendLineRunner(VoucherController voucherController, MemberController memberController) {
        this.voucherController = voucherController;
        this.memberController = memberController;
    }

    public void run(){
        boolean isRunning = true;
        while (isRunning){
            try {
                ConsoleOutput.printMenu();
                String getUserMenu = ConsoleInput.getUserMenu();
                Menu menu = Menu.getMenu(getUserMenu);
                isRunning = executeAction(menu);

            }catch (InvalidInputException e){
                ConsoleOutput.printError();
            }catch (Exception e){
                logger.error(e.toString());
            }
        }
    }

    private boolean executeAction(Menu menu) throws IOException {
        if (menu == Menu.CREATE){
            voucherController.create();
            return true;
        }
        if (menu == Menu.EXIT){
            return false;
        }
        if (menu == Menu.LIST){
            voucherController.findAll();
            return true;
        }
        if (menu == Menu.BLACK_LIST){
            memberController.findAllBlackMember();
            return true;
        }
        throw new InvalidInputException();
    }
}
