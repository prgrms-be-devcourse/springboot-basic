package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherResponseDto {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Long discount;

    public VoucherResponseDto(UUID voucherId, VoucherType voucherType, Long discount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Long getDiscount() {
        return discount;
    }
}
