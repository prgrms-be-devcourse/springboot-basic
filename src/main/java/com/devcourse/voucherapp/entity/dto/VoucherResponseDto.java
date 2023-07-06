package com.devcourse.voucherapp.entity.dto;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.UUID;
import lombok.Getter;

@Getter
public class VoucherResponseDto {

    private final UUID id;
    private final VoucherType type;
    private final int discountAmount;

    public VoucherResponseDto(Voucher voucher) {
        this.id = voucher.getId();
        this.type = voucher.getType();
        this.discountAmount = voucher.getDiscountAmount();
    }

    @Override
    public String toString() {
        return format("{0} | {1} | {2}{3}", id, type.getName(), discountAmount, type.getUnit());
    }
}
