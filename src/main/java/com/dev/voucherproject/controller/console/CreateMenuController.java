package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.storage.VoucherStorage;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CreateMenuController extends MenuUsingConsoleAndStorage {
    public CreateMenuController(VoucherStorage voucherStorage, Console console) {
        super(voucherStorage, console);
    }

    @Override
    public void execute() {
        VoucherPolicy voucherPolicy;
        console.printSelectVoucherPolicy();

        String input = console.inputVoucherPolicySelection();
        voucherPolicy = VoucherPolicy.convertStringInputToPolicy(input);

        long discountNumber = selectPolicy(voucherPolicy);
        Voucher voucher = Voucher.of(voucherPolicy, UUID.randomUUID(), discountNumber);

        voucherStorage.insert(voucher);
    }

    private long selectPolicy(VoucherPolicy voucherPolicy) {
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
