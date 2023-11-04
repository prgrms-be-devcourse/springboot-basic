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
public class ModeController {

    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final WalletController walletController;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    private static final Logger logger = LoggerFactory.getLogger(ModeController.class);
    private static final String lineSeparator = System.lineSeparator();

    private final String MODE_WALLET = "wallet";
    private final String MODE_CUSTOMER = "customer";
    private final String MODE_VOUCHER = "voucher";
    private final String MODE_EXIT = "mode";

    public ModeController(CustomerController customerController, VoucherController voucherController,
                          WalletController walletController, InputHandler inputHandler, OutputHandler outputHandler) {
        this.customerController = customerController;
        this.voucherController = voucherController;
        this.walletController = walletController;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public boolean modeStart() throws IOException {
        String menu = modeSelect();

        switch (menu) {
            case MODE_CUSTOMER:
                customerController.customerMenu();
                break;
            case MODE_VOUCHER:
                voucherController.voucherMenu();
                break;
            case MODE_WALLET:
                walletController.walletMenu();
                break;
            case MODE_EXIT:
                outputHandler.outputString(EXIT_PROGRAM.getMessage());
                return false; // 프로그램 종료
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                break;
        }

        return true; // 프로그램 유지
    }

    private String modeSelect() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Program ===" + lineSeparator);
        sb.append("[1] Type 'customer' to enter a customer menu." + lineSeparator);
        sb.append("[2] Type 'voucher' to enter a voucher menu." + lineSeparator);
        sb.append("[3] Type 'wallet' to enter a wallet menu." + lineSeparator);
        sb.append("[4] Type 'exit' to exit the program." + lineSeparator);

        outputHandler.outputString(sb.toString());
        return inputHandler.inputString();
    }
}
