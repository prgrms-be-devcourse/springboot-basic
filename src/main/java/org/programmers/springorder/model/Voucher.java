package org.programmers.springorder.model;

import org.programmers.springorder.dto.VoucherRequestDto;

import java.util.Objects;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    public Voucher(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    private Voucher(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        this.voucherId = voucherId;
        this.discountValue = voucherRequestDto.getDiscountValue();
        this.voucherType = voucherRequestDto.getVoucherType();
    }

    public static Voucher of(UUID voucherId, VoucherRequestDto requestDto){
        return new Voucher(voucherId, requestDto);
    }

    public long getCalculate(long beforeDiscount){
        return this.voucherType.calculate(beforeDiscount, this.discountValue);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return discountValue == voucher.discountValue && Objects.equals(voucherId, voucher.voucherId) && voucherType == voucher.voucherType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountValue, voucherType);
    }

}
