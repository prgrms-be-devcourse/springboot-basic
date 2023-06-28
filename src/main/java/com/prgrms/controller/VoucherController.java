package com.prgrms.controller;

import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherList;
import com.prgrms.model.voucher.VoucherPolicy;
import com.prgrms.service.voucher.VoucherService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher(VoucherRequest voucherRequest) {
         voucherService.createVoucher(voucherRequest);
    }

    public VoucherList listVoucher() {
        return voucherService.getAllVoucherList();
    }
}
