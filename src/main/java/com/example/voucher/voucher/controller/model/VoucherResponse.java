package com.example.voucher.voucher.controller.model;

import java.util.Arrays;
import java.util.List;

import com.example.voucher.voucher.service.dto.VoucherDTO;

public class VoucherResponse {

    private final List<VoucherDTO> vouchers;

    public VoucherResponse(List<VoucherDTO> vouchers) {
        this.vouchers = vouchers;
    }

    public VoucherResponse(VoucherDTO voucher) {
        this.vouchers = Arrays.asList(voucher);
    }

    public List<VoucherDTO> getVouchers() {
        return vouchers;
    }
}
