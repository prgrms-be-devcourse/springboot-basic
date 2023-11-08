package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.controller.CustomerConsoleController;
import org.prgrms.kdt.voucher.controller.VoucherConsoleController;
import org.prgrms.kdt.wallet.WalletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static org.prgrms.kdt.io.Mode.MODE_MENU;
import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_NOT_EXIST_MENU;
import static org.prgrms.kdt.io.SystemMessage.EXIT_PROGRAM;

@Controller
public class ModeController {

    private final CustomerConsoleController customerConsoleController;
    private final VoucherConsoleController voucherConsoleController;
    private final WalletController walletController;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    private static final Logger logger = LoggerFactory.getLogger(ModeController.class);

    public ModeController(CustomerConsoleController customerConsoleController, VoucherConsoleController voucherConsoleController,
                          WalletController walletController, InputHandler inputHandler, OutputHandler outputHandler) {
        this.customerConsoleController = customerConsoleController;
        this.voucherConsoleController = voucherConsoleController;
        this.walletController = walletController;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public boolean modeStart() throws IOException {
        String userInput = modeSelect();
        Mode menu = Mode.fromString(userInput);

        switch (menu) {
            case MODE_CUSTOMER:
                customerConsoleController.customerMenu();
                break;
            case MODE_VOUCHER:
                voucherConsoleController.voucherMenu();
                break;
            case MODE_WALLET:
                walletController.walletMenu();
                break;
            case MODE_EXIT:
                outputHandler.outputString(EXIT_PROGRAM.getMessage());
                System.exit(0);
                break;
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                break;
        }

        return true; // 프로그램 유지
    }

    private String modeSelect() throws IOException {
        outputHandler.outputString(MODE_MENU.getMessage());
        return inputHandler.inputString();
    }
}
