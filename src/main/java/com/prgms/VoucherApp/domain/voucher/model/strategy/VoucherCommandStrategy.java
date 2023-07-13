package com.prgms.VoucherApp.domain.voucher.model.strategy;

import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

public interface VoucherCommandStrategy {

    void execute(Input input, Output output, VoucherService voucherService);
}
