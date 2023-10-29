package org.prgrms.kdt.wallet;

import org.prgrms.kdt.io.InputHandler;
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
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    private static final String lineSeparator = System.lineSeparator();

    private final String CREATE = "create";
    private final String REMOVE = "remove";

    public WalletController(WalletService walletService, InputHandler inputHandler, OutputHandler outputHandler) {
        this.walletService = walletService;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public void walletMenu() throws IOException {
        String menu = selectWalletMenu();

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
                outputHandler.outputString(errorMessage);
                break;
        }
    }

    private String selectWalletMenu() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Wallet Program ===");
        sb.append(lineSeparator);
        sb.append("[1] Type 'create' to create a new wallet.");
        sb.append(lineSeparator);
        sb.append("[2] Type 'remove' to remove a wallet.");
        sb.append(lineSeparator);

        outputHandler.outputString(sb.toString());

        return inputHandler.inputString();
    }

    private void addWallet() throws IOException {
        outputHandler.outputString(ADD_CUSTOMER_ID.getMessage());
        String customerId = inputHandler.inputString();
        outputHandler.outputString(ADD_VOUCHER_ID.getMessage());
        String voucherId = inputHandler.inputString();

        walletService.addWallet(customerId, voucherId);

        outputHandler.outputString(COMPLETE_ADD.getMessage());
    }

    private void removeWalletByCustomerId() throws IOException {
        outputHandler.outputString(REMOVE_CUSTOMER_ID.getMessage());
        String customerId = inputHandler.inputString();

        walletService.deleteByCustomerId(customerId);

        outputHandler.outputString(COMPLETE_REMOVE.getMessage());
    }
}
