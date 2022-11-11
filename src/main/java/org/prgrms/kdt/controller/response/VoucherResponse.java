package org.prgrms.kdt.controller.response;

import org.prgrms.kdt.domain.Voucher;

public class VoucherResponse {

    private final Voucher voucher;

    public VoucherResponse(Voucher voucher) {
        this.voucher = voucher;
    }

    public Voucher getVoucher() {
        return voucher;
    }
}
