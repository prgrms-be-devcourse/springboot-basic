package com.prgrms.w3springboot.io.strategy;

import com.prgrms.w3springboot.io.Console;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.voucher.service.VoucherService;

public class CreateCommand implements CommandStrategy {
    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        while (true) {
            console.printTypeChoice();
            String voucherType = console.input();
            console.printDiscountAmountChoice();
            String discountAmount = console.input();

            Voucher createdVoucherId;
            try {
                createdVoucherId = voucherService.createVoucher(VoucherType.of(voucherType), Long.parseLong(discountAmount));
            } catch (Exception e) {
                console.printInvalidMessage();
                continue;
            }

            console.printVoucher(createdVoucherId);
            break;
        }

        return true;
    }
}
