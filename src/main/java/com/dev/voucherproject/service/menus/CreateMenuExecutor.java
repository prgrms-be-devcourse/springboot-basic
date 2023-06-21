package com.dev.voucherproject.service.menus;

import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.service.VoucherService;
import com.dev.voucherproject.view.Console;

import java.util.UUID;

public class CreateMenuExecutor extends SelectMenuExecutor {
    public CreateMenuExecutor(Menu menu, VoucherService voucherService, Console console) {
        super(menu, voucherService, console);
    }

    @Override
    public void execute(Menu menu) {
        if (isSatisfiedBy(menu)) {
            VoucherPolicy voucherPolicy;
            console.printSelectVoucherPolicy();
            voucherPolicy = console.inputVoucherPolicySelection();

            Voucher voucher = createVoucher(voucherPolicy);
            voucherService.insert(voucher);
        }
    }

    private Voucher createVoucher(VoucherPolicy voucherPolicy) {
        UUID uuid = UUID.randomUUID();

        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            console.printFixAmountPolicyArgs();
            long amount = console.inputAmount();
            return new FixedAmountVoucher(uuid, amount);
        }

        console.printDiscountVoucherPolicyArgs();
        long percent = console.inputPercent();
        return new PercentDiscountVoucher(uuid, percent);
    }
}
