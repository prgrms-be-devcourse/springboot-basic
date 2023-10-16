package com.zerozae.voucher.dto.voucher;

import com.zerozae.voucher.domain.voucher.VoucherType;
import lombok.Getter;

@Getter
public class VoucherRequest {
    private long discount;
    private VoucherType voucherType;

    public VoucherRequest(long discount, VoucherType voucherType) {
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
