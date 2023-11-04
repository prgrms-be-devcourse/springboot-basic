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
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";

    private static final String lineSeparator = System.lineSeparator();
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
            case CREATE:
                voucherAdd();
                break;
            case OWNER:
                getOwner();
                break;
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                break;
        }
    }

    public String selectVoucherMenu() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===" + lineSeparator);
        sb.append("[1] Type 'create' to create a new voucher." + lineSeparator);
        sb.append("[2] Type 'owner' to view customer with a specific voucher." + lineSeparator);

        outputHandler.outputString(sb.toString());

        return inputHandler.inputString();
    }

    private void getOwner() throws IOException {
        outputHandler.outputString(GET_OWNER.getMessage());
        UUID voucherId = UUID.fromString(inputHandler.inputString());
        Optional<Wallet> wallet = voucherService.getOwner(voucherId);
        outputHandler.outputWallet(wallet);
    }

    private void voucherAdd() throws IOException {
        while (true) {
            outputHandler.outputString(CREATE_VOUCHER_TYPE.getMessage());
            var createVoucherType = inputHandler.inputString();

            var isRepeat = true;

            if (createVoucherType.equals(FIXED)) {
                isRepeat = fixedAmountVoucherAdd();
                if(!isRepeat)
                    break;
            } else if (createVoucherType.equals(PERCENT)) {
                isRepeat = percentDiscountVoucherAdd();
                if(!isRepeat)
                    break;
            } else {
                String errorMessage = EXCEPTION_VOUCHER_TYPE.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
            }
        }
    }

    private boolean fixedAmountVoucherAdd() throws IOException {
        outputHandler.outputString(CREATE_FIXED_VOUCHER.getMessage());
        var amount = inputHandler.inputInt();
        fixedAmountVoucherService.createFixedAmountVoucher(amount);
        return true;
    }

    private boolean percentDiscountVoucherAdd() throws IOException {
        outputHandler.outputString(CREATE_PERCENT_VOUCHER.getMessage());
        var percent = inputHandler.inputInt();
        percentDiscountVoucherService.createPercentDiscountVoucher(percent);
        return true;
    }
}
