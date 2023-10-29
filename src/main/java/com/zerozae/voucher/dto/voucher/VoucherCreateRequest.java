package com.zerozae.voucher.dto.voucher;

import com.zerozae.voucher.domain.voucher.VoucherType;
import lombok.Getter;

@Getter
public class VoucherCreateRequest {

    private long discount;
    private VoucherType voucherType;

    public VoucherCreateRequest(long discount, VoucherType voucherType) {
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
