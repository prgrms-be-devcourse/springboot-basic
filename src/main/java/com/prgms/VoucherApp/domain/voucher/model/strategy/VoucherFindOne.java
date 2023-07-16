package com.prgms.VoucherApp.domain.voucher.model.strategy;

import com.prgms.VoucherApp.domain.voucher.dto.VoucherResponse;
import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.util.UUID;

public class VoucherFindOne implements VoucherCommandStrategy {

    @Override
    public void execute(Input input, Output output, VoucherService voucherService) {
        String inputUUID = input.inputUUID();
        UUID voucherId = UUID.fromString(inputUUID);

        VoucherResponse voucherResponse = voucherService.findOne(voucherId);
        output.printVoucher(voucherResponse);
    }
}
