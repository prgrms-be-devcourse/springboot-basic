package org.prgrms.kdt.wallet;

import org.prgrms.kdt.io.InputHandler;
import org.prgrms.kdt.io.MenuController;
import org.prgrms.kdt.io.OutputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_NOT_EXIST_MENU;
import static org.prgrms.kdt.wallet.WalletMessage.*;

@Controller
public class WalletController {

    private final WalletService walletService;
    private final MenuController menuController;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final String CREATE = "create";
    private final String REMOVE = "remove";

    public WalletController(WalletService walletService, MenuController menuController, InputHandler inputHandler, OutputHandler outputHandler) {
        this.walletService = walletService;
        this.menuController = menuController;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public void walletMenu() throws IOException {
        String menu = menuController.walletMenu();

        switch (menu) {
            case CREATE:
                addWallet();
                break;
            case REMOVE:
                removeWalletByCustomerId();
                break;
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputSystemMessage(errorMessage);
                break;
        }
    }

    private void addWallet() throws IOException {
        outputHandler.outputSystemMessage(INPUT_CUSTOMER_ID.getMessage());
        String customerId = inputHandler.inputString();
        outputHandler.outputSystemMessage(INPUT_VOUCHER_ID.getMessage());
        String voucherId = inputHandler.inputString();

        walletService.addWallet(customerId, voucherId);

        outputHandler.outputSystemMessage(COMPLETE_ADD.getMessage());
    }

    private void removeWalletByCustomerId() throws IOException {
        outputHandler.outputSystemMessage(INPUT_CUSTOMER_ID.getMessage());
        String customerId = inputHandler.inputString();

        walletService.deleteByCustomerId(customerId);

        outputHandler.outputSystemMessage(COMPLETE_REMOVE.getMessage());
    }
}
