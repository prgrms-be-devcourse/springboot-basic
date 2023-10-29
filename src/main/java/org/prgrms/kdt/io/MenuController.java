package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.CustomerController;
import org.prgrms.kdt.voucher.VoucherController;
import org.prgrms.kdt.wallet.WalletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_NOT_EXIST_MENU;
import static org.prgrms.kdt.io.SystemMessage.EXIT_PROGRAM;

@Controller
public class MenuController {

    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final WalletController walletController;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    private static final String lineSeparator = System.lineSeparator();

    private final String CUSTOMER = "customer";
    private final String VOUCHER = "voucher";
    private final String WALLET = "wallet";
    private final String EXIT = "exit";

    public MenuController(CustomerController customerController, VoucherController voucherController,
                          WalletController walletController, InputHandler inputHandler, OutputHandler outputHandler) {
        this.customerController = customerController;
        this.voucherController = voucherController;
        this.walletController = walletController;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public boolean startMenu() throws IOException {
        String menu = selectMenu();

        switch (menu) {
            case CUSTOMER:
                customerController.customerMenu();
                break;
            case VOUCHER:
                voucherController.voucherMenu();
                break;
            case WALLET:
                walletController.walletMenu();
                break;
            case EXIT:
                outputHandler.outputString(EXIT_PROGRAM.getMessage());
                return false;
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                break;
        }

        return true;
    }

    private String selectMenu() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Program ===");
        sb.append(lineSeparator);
        sb.append("[1] Type 'customer' to enter a customer menu.");
        sb.append(lineSeparator);
        sb.append("[2] Type 'voucher' to enter a voucher menu.");
        sb.append(lineSeparator);
        sb.append("[3] Type 'wallet' to enter a wallet menu.");
        sb.append(lineSeparator);
        sb.append("[4] Type 'exit' to exit the program.");
        sb.append(lineSeparator);

        outputHandler.outputString(sb.toString());

        return inputHandler.inputString();
    }
}
