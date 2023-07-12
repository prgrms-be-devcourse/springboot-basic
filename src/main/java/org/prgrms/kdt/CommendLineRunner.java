package org.prgrms.kdt;

import org.prgrms.kdt.commendLine.Console;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.util.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Profile("!test")
@Component
public class CommendLineRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommendLineRunner.class);

    private final ControllerRequestManager controllerRequestManager;
    private final Console console;

    public CommendLineRunner(ControllerRequestManager controllerRequestManager, Console console) {
        this.controllerRequestManager = controllerRequestManager;
        this.console = console;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                console.printMenu();
                int getUserMenu = Integer.parseInt(console.getUserMenu());
                Menu menu = Menu.getMenu(getUserMenu);
                isRunning = menu.isNotExit();
                executeAction(menu);

            } catch (InvalidInputException e) {
                console.printError();
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }

    // handler mapping
    private void executeAction(Menu menu) throws IOException {
        switch (menu) {
            case CREATE -> controllerRequestManager.createVoucher();

            case LIST -> controllerRequestManager.findAllVoucher();

            case BLACK_LIST -> controllerRequestManager.findAllBlackMember();

            case CREATE_MEMBER -> controllerRequestManager.createMember();

            case MEMBER_LIST -> controllerRequestManager.findAllMember();

            case ASSIGN_VOUCHER -> controllerRequestManager.assignVoucher();

            case VOUCHER_LIST_BY_MEMBER -> controllerRequestManager.findVouchersByMember();

            case DELETE_WALLET -> controllerRequestManager.deleteWalletById();

            case MEMBER_LIST_BY_VOUCHER -> controllerRequestManager.findMembersByVoucher();

            case WALLET_LIST -> controllerRequestManager.findAllWallet();
        }
    }
}
