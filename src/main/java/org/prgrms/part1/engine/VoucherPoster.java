package org.prgrms.part1.engine;

import org.prgrms.part1.io.Output;
import org.slf4j.Logger;

public class VoucherPoster implements Runnable{
    private final VoucherService voucherService;
    private final Output output;

    public VoucherPoster(VoucherService voucherService, Output output) {
        this.voucherService = voucherService;
        this.output = output;
    }

    @Override
    public void run() {
        if (voucherService.getAllVouchers().isEmpty()) {
            output.print("Voucher List is empty!");
            return;
        }

        output.print("Show Voucher List\n");
        voucherService.getAllVouchers().forEach(v -> {
            output.print("Voucher Id : " + v.getVoucherId());
            output.print("Voucher Type : " + v.getVoucherType());
            output.print("Discount Amount : " + v.getValue());
            output.print("");
        });
    }
}
