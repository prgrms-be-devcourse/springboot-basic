package com.devcourse.voucherapp.entity.voucher.response;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoucherResponseDto {

    private final UUID id;
    private final VoucherType type;
    private final int discountAmount;

    public static VoucherResponseDto from(Voucher voucher) {
        return new VoucherResponseDto(voucher.getId(), voucher.getType(), voucher.getDiscountAmount());
    }

    @Override
    public String toString() {
        return format("{0} | {1} | {2}{3}", id, type.getName(), discountAmount, type.getUnit());
    }
}
