package com.example.demo.dto;

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

    public UUID getId() {
        return id;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

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
