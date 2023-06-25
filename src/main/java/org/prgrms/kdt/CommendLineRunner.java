package org.prgrms.kdt;

import org.prgrms.kdt.commendLine.ConsoleInput;
import org.prgrms.kdt.commendLine.ConsoleOutput;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommendLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommendLineRunner.class);
    private final VoucherController voucherController;

    public CommendLineRunner(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    public void run(){
        boolean isRunning = true;
        while (isRunning){
            try {
                ConsoleOutput.printMenu();
                String getUserMenu = ConsoleInput.getUserMenu();
                Menu menu = Menu.getMenu(getUserMenu);
                isRunning = executeAction(menu);

            }catch (IOException | InvalidInputException e){
                ConsoleOutput.printError();
            }catch (Exception e){
                logger.error("예상하지 못한 예외가 발생했습니다.");
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
        return true;
    }
}
