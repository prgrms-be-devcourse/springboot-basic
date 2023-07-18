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

    private final ViewManager viewManager;
    private final Console console;

    public CommendLineRunner(ViewManager viewManager, Console console) {
        this.viewManager = viewManager;
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

    private void executeAction(Menu menu) throws IOException {
        switch (menu) {
            case CREATE -> viewManager.createVoucher();

            case LIST -> viewManager.findAllVoucher();

            case BLACK_LIST -> viewManager.findAllBlackMember();

            case CREATE_MEMBER -> viewManager.createMember();

            case MEMBER_LIST -> viewManager.findAllMember();

            case ASSIGN_VOUCHER -> viewManager.assignVoucher();

            case VOUCHER_LIST_BY_MEMBER -> viewManager.findVouchersByMember();

            case DELETE_WALLET -> viewManager.deleteWalletById();

            case MEMBER_LIST_BY_VOUCHER -> viewManager.findMembersByVoucher();

            case WALLET_LIST -> viewManager.findAllWallet();
        }
    }
}
