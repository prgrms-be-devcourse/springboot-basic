package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public class VoucherListResponse {
    private final List<Voucher> voucherList;

    public VoucherListResponse(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }
}
