package com.example.demo.dto;

import com.example.demo.domain.voucher.Voucher;
import com.example.demo.util.VoucherType;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherDto {

    private final UUID id;
    private final double discountAmount;
    private final VoucherType voucherType;

    public String formatAsString() {
        return switch (voucherType) {
            case FIX -> String.format("    Fixed Voucher, Discount Amount: %.0f (바우처 ID : %s)", discountAmount, id.toString());
            case PERCENT -> String.format("    Percent Voucher, Discount percent Amount: %.0f (바우처 ID : %s)", discountAmount, id.toString());
            default -> throw new IllegalArgumentException("잘 못된 바우처 타입입니다.");
        };
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getId(), voucher.getDiscountAmount(), voucher.getVoucherType());
    }
    
}
