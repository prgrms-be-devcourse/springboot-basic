package org.prgrms.kdt.application.voucher;

import org.prgrms.kdt.application.voucher.io.Input;
import org.prgrms.kdt.application.voucher.io.Output;
import org.prgrms.kdt.domain.voucher.service.VoucherService;

public class VoucherApplication implements Runnable{
    private VoucherService voucherService;
    private Input input;
    private Output output;

    public VoucherApplication(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {

    }
}
