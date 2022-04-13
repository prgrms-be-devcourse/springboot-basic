package org.prgrms.part1.engine;

import org.prgrms.part1.engine.service.VoucherService;
import org.prgrms.part1.io.Output;

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
        voucherService.getAllVouchers().forEach(v -> output.print(v.toString() + "\n"));
    }
}
