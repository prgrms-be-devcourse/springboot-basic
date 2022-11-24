package com.example.springbootbasic.dto.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;

public class VoucherDto {

    private Long voucherId;
    private Long discountValue;
    private VoucherType voucherType;

    public VoucherDto() {
    }

    public VoucherDto(Long discountValue, VoucherType voucherType) {
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public VoucherDto(Long voucherId, Long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public static VoucherDto newInstance(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getDiscountValue(), voucher.getVoucherType());
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public void setDiscountValue(Long discountValue) {
        this.discountValue = discountValue;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public boolean isEmpty() {
        return voucherId == 0L || voucherType == null;
    }
}
