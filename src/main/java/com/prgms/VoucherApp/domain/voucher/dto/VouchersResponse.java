package com.prgms.VoucherApp.domain.voucher.dto;

import java.util.Collections;
import java.util.List;

public class VouchersResDto {

    private final List<VoucherResponse> vouchers;

    public VouchersResDto(List<VoucherResponse> vouchers) {
        this.vouchers = vouchers;
    }

    public boolean isEmpty() {
        return vouchers.isEmpty();
    }

    public List<VoucherResponse> getVouchers() {
        return Collections.unmodifiableList(vouchers);
    }
}
