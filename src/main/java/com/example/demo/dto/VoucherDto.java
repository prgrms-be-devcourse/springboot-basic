package com.example.demo.dto;

import com.example.demo.domain.voucher.FixedAmountVoucher;
import com.example.demo.domain.voucher.Voucher;
import com.example.demo.util.VoucherType;
import java.util.UUID;

public class VoucherDto {

    private final UUID id;
    private final double discountAmount;
    private final VoucherType voucherType;

    public VoucherDto(UUID id, double discountAmount, VoucherType voucherType) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }

    @Override
    public String toString() {
        return switch (voucherType) {
            case FIX -> String.format("    Fixed Voucher, Discount Amount: %.0f 할인권이 생성되었습니다.", discountAmount);
            case PERCENT -> String.format("    Percent Voucher, Discount percent Amount: %.0f 할인권이 생성되었습니다.", discountAmount);
            default -> throw new IllegalArgumentException("잘 못된 바우처 타입입니다.");
        };
    }

    public static VoucherDto from(Voucher voucher) {
        VoucherType type = voucher instanceof FixedAmountVoucher ? VoucherType.FIX : VoucherType.PERCENT;
        return new VoucherDto(voucher.getId(), voucher.getDiscountAmount(), type);
    }
}
