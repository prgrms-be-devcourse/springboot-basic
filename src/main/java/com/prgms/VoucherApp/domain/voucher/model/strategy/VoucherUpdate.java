package com.prgms.VoucherApp.domain.voucher.model.strategy;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherUpdateRequest;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.math.BigDecimal;
import java.util.UUID;

public class VoucherUpdate implements VoucherCommandStrategy {

    @Override
    public void execute(Input input, Output output, VoucherService voucherService) {
        String inputUUID = input.inputUUID();
        UUID voucherId = UUID.fromString(inputUUID);

        String inputVoucherType = input.inputVoucherType();
        VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);

        Long inputAmount = input.inputDiscountAmount(voucherType);
        BigDecimal amount = BigDecimal.valueOf(inputAmount);

        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(voucherId, amount, voucherType);
        voucherService.update(voucherUpdateRequest);
    }
}
