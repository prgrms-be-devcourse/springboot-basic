package org.prgrms.kdt.voucher;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.io.InputHandler;
import org.prgrms.kdt.io.OutputHandler;
import org.prgrms.kdt.voucher.Dto.FixedAmountVoucherDto;
import org.prgrms.kdt.voucher.Dto.PercentDiscountVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.UUID;

import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_NOT_EXIST_MENU;
import static org.prgrms.kdt.voucher.VoucherMessage.*;

@Controller
public class VoucherController {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final VoucherService voucherService;

    private final String CREATE = "create";
    private final String OWNER = "owner";
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";

    private static final String lineSeparator = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(InputHandler inputHandler, OutputHandler outputHandler, VoucherService voucherService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.voucherService = voucherService;
    }

    public void voucherMenu() throws IOException {
        String menu = selectVoucherMenu();

        switch (menu) {
            case CREATE:
                createVoucher();
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
        sb.append("=== Voucher Program ===");
        sb.append(lineSeparator);
        sb.append("[1] Type 'create' to create a new voucher.");
        sb.append(lineSeparator);
        sb.append("[2] Type 'owner' to view customer with a specific voucher.");
        sb.append(lineSeparator);

        outputHandler.outputString(sb.toString());

        return inputHandler.inputString();
    }

    private void getOwner() throws IOException {
        outputHandler.outputString(GET_OWNER.getMessage());
        UUID voucherId = UUID.fromString(inputHandler.inputString());
        Customer customer = voucherService.getOwner(voucherId);
        outputHandler.outputCustomer(customer);
    }

    private void createVoucher() throws IOException {
        while (true) {
            outputHandler.outputString(CREATE_VOUCHER_TYPE.getMessage());
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
                outputHandler.outputString(errorMessage);
            }
        }
    }

    private boolean createFixedAmountVoucher() throws IOException {
        outputHandler.outputString(CREATE_FIXED_VOUCHER.getMessage());

        var amount = 0;
        while (true) {
            amount = inputHandler.inputInt();

            if (amount <= 0) {
                String errorMessage = EXCEPTION_FIXED_AMOUNT_MINUS.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                continue;
            }
            if (amount >= 100_000) {
                String errorMessage = EXCEPTION_FIXED_AMOUNT_OVER.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                continue;
            }
            break;
        }

        var fixedAmountVoucherDto = new FixedAmountVoucherDto(UUID.randomUUID(), amount);
        voucherService.createVoucher(fixedAmountVoucherDto);
        return false;
    }

    private boolean createPercentDiscountVoucher() throws IOException {
        outputHandler.outputString(CREATE_PERCENT_VOUCHER.getMessage());

        var percent = 0;
        while (true) {
            percent = inputHandler.inputInt();

            if (percent <= 0) {
                String errorMessage = EXCEPTION_PERCENT_MINUS.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                continue;
            }
            if (percent >= 100) {
                String errorMessage = EXCEPTION_PERCENT_OVER.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                continue;
            }
            break;
        }

        var percentDiscountVoucherDto = new PercentDiscountVoucherDto(UUID.randomUUID(), percent);
        voucherService.createVoucher(percentDiscountVoucherDto);
        return false;
    }
}
