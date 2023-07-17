package com.devcourse.voucherapp.entity.voucher.dto;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.entity.voucher.VoucherType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherResponseDto {

    private UUID id;
    private VoucherType type;
    private int discountAmount;

    public static VoucherResponseDto from(Voucher voucher) {
        return new VoucherResponseDto(voucher.getId(), voucher.getType(), voucher.getDiscountAmount());
    }

    @Override
    public String toString() {
        return format("{0} | {1} | {2}{3}", id, type.getName(), discountAmount, type.getUnit());
    }
}
