package org.prgrms.kdt.voucher;

import org.prgrms.kdt.io.InputHandler;
import org.prgrms.kdt.io.OutputHandler;
import org.prgrms.kdt.io.MenuController;
import org.prgrms.kdt.customer.CustomerController;
import org.prgrms.kdt.voucher.Dto.FixedAmountVoucherDto;
import org.prgrms.kdt.voucher.Dto.PercentDiscountVoucherDto;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.wallet.WalletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.io.SystemMessage.*;
import static org.prgrms.kdt.voucher.VoucherMessage.*;

@Controller
public class VoucherController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final MenuController menuController;
    private final VoucherService voucherService;
    private final CustomerController customerController;
    private final WalletController walletController;
    private final String EXIT = "exit";
    private final String CREATE = "create";
    private final String LIST = "list";
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";
    private final String BLACK = "black";
    private final String WALLET = "wallet";
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(InputHandler inputHandler, OutputHandler outputHandler, MenuController menuController, VoucherService voucherService, CustomerController customerController, WalletController walletController) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.menuController = menuController;
        this.voucherService = voucherService;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    public boolean startVoucherMenu() throws IOException {
        String menu = menuController.startMenu();

        switch (menu) {
            case EXIT:
                outputHandler.outputSystemMessage(EXIT_PROGRAM.getMessage());
                return false;
            case CREATE:
                createVoucher();
                break;
            case LIST:
                List<Voucher> voucherList = voucherService.getAllVouchers();
                outputHandler.outputVouchers(voucherList);
                break;
            case BLACK:
                customerController.getBlackList();
                break;
            case WALLET:
                walletController.walletMenu();
                break;
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputSystemMessage(errorMessage);
                break;
        }

        return true;
    }

    private void createVoucher() throws IOException {
        while (true) {
            outputHandler.outputSystemMessage(CREATE_VOUCHER_TYPE.getMessage());
            var createVoucherType = inputHandler.inputString();

            var isRepeat = true;

            if (createVoucherType.equals(FIXED)) {
                isRepeat = createFixedAmountVoucher();
                if(!isRepeat)
                    break;
            } else if (createVoucherType.equals(PERCENT)) {
                isRepeat = createPercentDiscountVoucher();
                if(!isRepeat)
                    break;
            } else {
                String errorMessage = EXCEPTION_VOUCHER_TYPE.getMessage();
                logger.error(errorMessage);
                outputHandler.outputSystemMessage(errorMessage);
            }
        }
    }

    private boolean createFixedAmountVoucher() throws IOException {
        outputHandler.outputSystemMessage(CREATE_FIXED_VOUCHER.getMessage());

        var amount = 0;
        while (true) {
            amount = inputHandler.inputInt();

            if (amount <= 0) {
                String errorMessage = EXCEPTION_FIXED_AMOUNT_MINUS.getMessage();
                logger.error(errorMessage);
                outputHandler.outputSystemMessage(errorMessage);
                continue;
            }
            if (amount >= 100_000) {
                String errorMessage = EXCEPTION_FIXED_AMOUNT_OVER.getMessage();
                logger.error(errorMessage);
                outputHandler.outputSystemMessage(errorMessage);
                continue;
            }
            break;
        }

        var fixedAmountVoucherDto = new FixedAmountVoucherDto(UUID.randomUUID(), amount);
        voucherService.createVoucher(fixedAmountVoucherDto);
        return false;
    }

    private boolean createPercentDiscountVoucher() throws IOException {
        outputHandler.outputSystemMessage(CREATE_PERCENT_VOUCHER.getMessage());

        var percent = 0;
        while (true) {
            percent = inputHandler.inputInt();

            if (percent <= 0) {
                String errorMessage = EXCEPTION_PERCENT_MINUS.getMessage();
                logger.error(errorMessage);
                outputHandler.outputSystemMessage(errorMessage);
                continue;
            }
            if (percent >= 100) {
                String errorMessage = EXCEPTION_PERCENT_OVER.getMessage();
                logger.error(errorMessage);
                outputHandler.outputSystemMessage(errorMessage);
                continue;
            }
            break;
        }

        var percentDiscountVoucherDto = new PercentDiscountVoucherDto(UUID.randomUUID(), percent);
        voucherService.createVoucher(percentDiscountVoucherDto);
        return false;
    }
}
