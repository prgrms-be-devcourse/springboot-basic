package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.model.storage.voucher.VoucherStorage;
import com.dev.voucherproject.view.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CreateMenuController implements MenuController {
    private static final Logger logger = LoggerFactory.getLogger(CreateMenuController.class);
    private final Console console;
    private final VoucherStorage voucherStorage;

    public CreateMenuController(Console console, VoucherStorage voucherStorage) {
        this.console = console;
        this.voucherStorage = voucherStorage;
    }

    @Override
    public void execute() {
        console.printSelectVoucherPolicy();
        String input = console.inputVoucherPolicySelection();
        VoucherPolicy voucherPolicy = VoucherPolicy.convertStringInputToPolicy(input);

        long discountNumber = selectPolicy(voucherPolicy);

        voucherStorage.insert(Voucher.of(voucherPolicy, discountNumber, UUID.randomUUID()));
        logger.info("바우처 생성 :: [{}, {}]", voucherPolicy.name(), discountNumber);

        console.newLine();
    }

    private long selectPolicy(final VoucherPolicy voucherPolicy) {
        long discountNumber;

        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            console.printFixAmountPolicyArgs();
            discountNumber = console.inputAmount();

            return discountNumber;
        }

        console.printDiscountVoucherPolicyArgs();
        discountNumber = console.inputPercent();

        return discountNumber;
    }
}
