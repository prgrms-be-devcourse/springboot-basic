package com.prgms.VoucherApp.domain.voucher.dto;

import java.util.Collections;
import java.util.List;

public class VouchersResDto {

    private final List<VoucherResDto> vouchers;

    public VouchersResDto(List<VoucherResDto> vouchers) {
        this.vouchers = vouchers;
    }

    public boolean isEmpty() {
        return vouchers.isEmpty();
    }

    public List<VoucherResDto> getVouchers() {
        return Collections.unmodifiableList(vouchers);
    }
}
