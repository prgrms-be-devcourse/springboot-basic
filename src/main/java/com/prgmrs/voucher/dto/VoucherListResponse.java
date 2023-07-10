package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.model.Voucher;

import java.util.Map;
import java.util.UUID;

public class VoucherListResponse {
    private final Map<UUID, Voucher> voucherList;

    public VoucherListResponse(Map<UUID, Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherList;
    }
}
