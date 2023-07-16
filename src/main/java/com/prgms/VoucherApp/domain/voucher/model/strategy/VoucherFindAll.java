package com.prgms.VoucherApp.domain.voucher.model.strategy;

import com.prgms.VoucherApp.domain.voucher.dto.VouchersResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

public class VoucherFindAll implements VoucherCommandStrategy {

    @Override
    public void execute(Input input, Output output, VoucherService voucherService) {
        VouchersResponse findVouchers = voucherService.findAll();
        output.printVoucherList(findVouchers.getVouchers());
    }
}
