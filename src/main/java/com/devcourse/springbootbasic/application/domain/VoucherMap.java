package com.devcourse.springbootbasic.application.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VoucherMap {
    private final Map<UUID, Voucher> voucherMap;

    public VoucherMap(Map<UUID, Voucher> voucherMap) {
        this.voucherMap = voucherMap;
    }

    public void addVoucher(Voucher voucher) {
        voucherMap.put(voucher.voucherId, voucher);
    }

    public List<String> getAllVouchers() {
        return voucherMap.values()
                .stream()
                .map(Voucher::toString)
                .toList();
    }

}
