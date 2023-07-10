package com.prgms.VoucherApp.domain.voucher.dto;

import java.util.Collections;
import java.util.List;

public record VouchersResponse(
    List<VoucherResponse> vouchers
) {

    public boolean isEmpty() {
        return vouchers.isEmpty();
    }

    public List<VoucherResponse> getVouchers() {
        return Collections.unmodifiableList(vouchers);
    }
}
