package com.programmers.springweekly.dto;

import com.programmers.springweekly.domain.voucher.VoucherType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ReadVoucherDto {

    private final UUID voucherId;
    private final String discountAmount;
    private final VoucherType voucherType;

    public ReadVoucherDto(String voucherId, String discountAmount, String voucherType) {
        this.voucherId = UUID.fromString(voucherId);
        this.discountAmount = discountAmount;
        this.voucherType = VoucherType.from(voucherType);
    }
}
