package org.programmers.springorder.model.voucher;

import org.programmers.springorder.dto.voucher.VoucherRequestDto;

import java.util.UUID;


public class Voucher {
    private final UUID voucherId;
    private long discountValue;
    private VoucherType voucherType;

    private Voucher(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public static Voucher toVoucher(UUID voucherId, long discountValue, VoucherType voucherType) {
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

    public boolean isSameId(UUID comparingId) {
        return this.voucherId.equals(comparingId);
    }

    public long getCalculate(long beforeDiscount) {
        return this.voucherType.calculate(beforeDiscount, this.discountValue);
    }

    public void update(VoucherType voucherType, long discountValue) {
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public String getVoucherIdToString() {
        return this.voucherId.toString();
    }

    public String getVoucherTypeName() {
        return this.voucherType.name();
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


}
