package org.prgrms.kdt.domain.voucher.application;

import org.prgrms.kdt.domain.voucher.io.Input;
import org.prgrms.kdt.domain.voucher.io.Output;
import org.prgrms.kdt.domain.voucher.service.VoucherService;

public class VoucherCommandLineApplication implements Runnable{
    private VoucherService voucherService;
    private Input input;
    private Output output;

    public VoucherCommandLineApplication(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {

    }
}
