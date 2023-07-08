package com.prgrms.controller;

import com.prgrms.model.voucher.dto.VoucherRequest;
import com.prgrms.model.voucher.dto.VoucherResponse;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.service.voucher.VoucherService;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        return voucherService.createVoucher(voucherRequest);
    }

    public List<VoucherResponse> listVoucher() {
        return voucherService.getAllVoucherList();
    }
}
