package org.programers.vouchermanagement.voucher.dto;

import java.util.List;

public class VouchersResponse {

    private final List<VoucherResponse> vouchers;

    public VouchersResponse(List<VoucherResponse> vouchers) {
        this.vouchers = vouchers;
    }

    public List<VoucherResponse> getVouchers() {
        return vouchers;
    }
}
