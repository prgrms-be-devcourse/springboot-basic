package com.prgms.VoucherApp.domain.voucher.model.strategy;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateRequest;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.math.BigDecimal;

public class VoucherCreate implements VoucherCommandStrategy {

    @Override
    public void execute(Input input, Output output, VoucherService voucherService) {
        output.printVoucherPolicy();
        String inputVoucherType = input.inputVoucherType();

        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);
        output.printDiscountCondition(voucherType);
        Long amount = input.inputDiscountAmount(voucherType);

        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(voucherType, BigDecimal.valueOf(amount));

        voucherService.save(voucherCreateRequest);
    }
}
