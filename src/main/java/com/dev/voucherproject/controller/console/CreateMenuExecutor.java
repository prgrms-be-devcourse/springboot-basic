package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.menu.Menu;
import com.dev.voucherproject.model.voucher.*;
import com.dev.voucherproject.model.voucher.VoucherDataAccessor;
import com.dev.voucherproject.view.Console;

import java.util.UUID;

public class CreateMenuExecutor extends SelectMenuExecutor {
    public CreateMenuExecutor(Menu menu, VoucherDataAccessor voucherDataAccessor, Console console) {
        super(menu, voucherDataAccessor, console);
    }

    @Override
    public void execute(Menu menu) {
        if (isSatisfiedBy(menu)) {
            VoucherPolicy voucherPolicy;
            console.printSelectVoucherPolicy();
            voucherPolicy = console.inputVoucherPolicySelection();

            Voucher voucher = createVoucher(voucherPolicy);
            voucherDataAccessor.insert(voucher);
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
