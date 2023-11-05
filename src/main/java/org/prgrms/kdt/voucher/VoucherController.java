package org.prgrms.kdt.voucher;

import org.prgrms.kdt.io.InputHandler;
import org.prgrms.kdt.io.OutputHandler;
import org.prgrms.kdt.voucher.service.FixedAmountVoucherService;
import org.prgrms.kdt.voucher.service.PercentDiscountVoucherService;
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

@Controller
public class VoucherController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final VoucherService voucherService;
    private final FixedAmountVoucherService fixedAmountVoucherService;
    private final PercentDiscountVoucherService percentDiscountVoucherService;

    private final String CREATE = "create";
    private final String OWNER = "owner";
    private final String FIND = "find";
    private final String DETAILS = "details";
    private final String REMOVE = "remove";
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(
            InputHandler inputHandler,
            OutputHandler outputHandler,
            VoucherService voucherService,
            FixedAmountVoucherService fixedAmountVoucherService,
            PercentDiscountVoucherService percentDiscountVoucherService
    ) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.voucherService = voucherService;
        this.fixedAmountVoucherService = fixedAmountVoucherService;
        this.percentDiscountVoucherService = percentDiscountVoucherService;
    }

    public void voucherMenu() throws IOException {
        String menu = selectVoucherMenu();

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

    private void voucherDetails() throws IOException {
        outputHandler.outputString(INPUT_VOUCHER_ID.getMessage());
        UUID voucherId = UUID.fromString(inputHandler.inputString());
        outputHandler.outputVoucher(voucherService.getVoucher(voucherId));
    }

    private String selectVoucherMenu() throws IOException {
        outputHandler.outputString(SELECT_VOUCHER_MENU.getMessage());
        return inputHandler.inputString();
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

        if (createVoucherType.equals(FIXED)) {
            fixedAmountVoucherAdd();
        } else if (createVoucherType.equals(PERCENT)) {
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
        fixedAmountVoucherService.createFixedAmountVoucher(amount);
    }

    private void percentDiscountVoucherAdd() throws IOException {
        outputHandler.outputString(CREATE_PERCENT_VOUCHER.getMessage());
        var percent = inputHandler.inputInt();
        percentDiscountVoucherService.createPercentDiscountVoucher(percent);
    }

    private void voucherRemove() throws IOException {
        outputHandler.outputString(INPUT_VOUCHER_ID.getMessage());
        UUID voucherId = UUID.fromString(inputHandler.inputString());
        voucherService.removeVoucherById(voucherId);
    }
}
