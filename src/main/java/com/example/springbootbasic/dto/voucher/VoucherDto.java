package com.example.springbootbasic.dto.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;

public class VoucherDto {

    private Long voucherId;
    private Long voucherDiscountValue;
    private VoucherType voucherType;

    public VoucherDto() {
    }

    public VoucherDto(Long voucherDiscountValue, VoucherType voucherType) {
        this.voucherDiscountValue = voucherDiscountValue;
        this.voucherType = voucherType;
    }

    public VoucherDto(Long voucherId, Long voucherDiscountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.voucherDiscountValue = voucherDiscountValue;
        this.voucherType = voucherType;
    }

    public static VoucherDto newInstance(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getVoucherDiscountValue(), voucher.getVoucherType());
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getVoucherDiscountValue() {
        return voucherDiscountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
