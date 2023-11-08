package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.io.InputHandler;
import org.prgrms.kdt.io.OutputHandler;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_NOT_EXIST_MENU;
import static org.prgrms.kdt.voucher.VoucherMessage.*;
import static org.prgrms.kdt.voucher.domain.VoucherType.FIXED;
import static org.prgrms.kdt.voucher.domain.VoucherType.PERCENT;

@Controller
public class VoucherConsoleController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherConsoleController.class);

    public VoucherConsoleController(InputHandler inputHandler, OutputHandler outputHandler, VoucherService voucherService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.voucherService = voucherService;
    }

    public void voucherMenu() throws IOException {
        String userInput = selectVoucherMenu();
        VoucherMenu menu = VoucherMenu.fromString(userInput);

        switch (menu) {
            case FIND:
                voucherList();
                break;
            case DETAILS:
                voucherDetails();
                break;
            case CREATE:
                voucherAdd();
                break;
            case OWNER:
                getOwner();
                break;
            case REMOVE:
                voucherRemove();
                break;
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                break;
        }
    }

    private void voucherList() {
        outputHandler.outputVouchers(voucherService.getAllVouchers());
    }

    private void voucherDetails() {
        outputHandler.outputString(INPUT_VOUCHER_ID.getMessage());
        UUID voucherId = null;
        try {
            voucherId = UUID.fromString(inputHandler.inputString());
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_VOUCHER_ID.getMessage());
        }
        outputHandler.outputVoucher(voucherService.getVoucher(voucherId));
    }

    private String selectVoucherMenu() throws IOException {
        outputHandler.outputString(SELECT_VOUCHER_MENU.getMessage());
        return inputHandler.inputString().trim().toLowerCase(); // Lowercased to match ignore case
    }

    private void getOwner() throws IOException {
        outputHandler.outputString(INPUT_VOUCHER_ID.getMessage());
        UUID voucherId = UUID.fromString(inputHandler.inputString());
        Optional<Wallet> wallet = voucherService.getOwner(voucherId);
        outputHandler.outputWallet(wallet);
    }

    private void voucherAdd() throws IOException {
        outputHandler.outputString(CREATE_VOUCHER_TYPE.getMessage());
        var createVoucherType = inputHandler.inputString();

        if (createVoucherType.equals(FIXED.getType())) {
            fixedAmountVoucherAdd();
        } else if (createVoucherType.equals(PERCENT.getType())) {
            percentDiscountVoucherAdd();
        } else {
            String errorMessage = EXCEPTION_VOUCHER_TYPE.getMessage();
            logger.error(errorMessage);
            outputHandler.outputString(errorMessage);
        }
    }

    private void fixedAmountVoucherAdd() throws IOException {
        outputHandler.outputString(CREATE_FIXED_VOUCHER.getMessage());
        var amount = inputHandler.inputInt();
        voucherService.createFixedAmountVoucher(amount);
    }

    private void percentDiscountVoucherAdd() throws IOException {
        outputHandler.outputString(CREATE_PERCENT_VOUCHER.getMessage());
        var amount = inputHandler.inputInt();
        voucherService.createPercentDiscountVoucher(amount);
    }

    private void voucherRemove() throws IOException {
        outputHandler.outputString(INPUT_VOUCHER_ID.getMessage());
        UUID voucherId = UUID.fromString(inputHandler.inputString());
        voucherService.removeVoucherById(voucherId);
    }
}
