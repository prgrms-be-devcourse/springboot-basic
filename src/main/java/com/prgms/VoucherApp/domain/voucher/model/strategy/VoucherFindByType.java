package com.prgms.VoucherApp.domain.voucher.model.strategy;

import com.prgms.VoucherApp.domain.voucher.dto.VouchersResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

public class VoucherFindByType implements VoucherCommandStrategy {

    @Override
    public void execute(Input input, Output output, VoucherService voucherService) {
        String inputVoucherType = input.inputVoucherType();
        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);

        VouchersResponse findVouchers = voucherService.findByVoucherType(voucherType);

        output.printVoucherList(findVouchers.getVouchers());
    }
}
