package org.programmers.springorder.voucher.model;

import org.programmers.springorder.voucher.dto.VoucherRequestDto;

import java.util.Objects;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    private Voucher(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public static Voucher toVoucher(UUID voucherId, long discountValue, VoucherType voucherType){
        return new Voucher(voucherId, discountValue, voucherType);
    }

    private Voucher(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        this.voucherId = voucherId;
        this.discountValue = voucherRequestDto.getDiscountValue();
        this.voucherType = voucherRequestDto.getVoucherType();
    }

    public static Voucher of(UUID voucherId, VoucherRequestDto requestDto) {
        return new Voucher(voucherId, requestDto);
    }

    public String insertVoucherDataInFile() {
        StringBuilder data = new StringBuilder();
        data.append(this.voucherId).append(",")
                .append(this.discountValue).append(",")
                .append(this.voucherType.name());
        return data.toString();
    }

    public boolean isSameId(UUID comparingId){
        return this.voucherId.equals(comparingId);
    }

    public long getCalculate(long beforeDiscount) {
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
