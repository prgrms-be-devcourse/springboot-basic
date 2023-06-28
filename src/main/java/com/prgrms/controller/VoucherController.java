package com.prgrms.controller;

import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherList;
import com.prgrms.service.voucher.VoucherService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public Voucher createVoucher(VoucherRequest voucherRequest) {
         return voucherService.createVoucher(voucherRequest);
    }

    public VoucherList listVoucher() {
        return voucherService.getAllVoucherList();
    }
}
