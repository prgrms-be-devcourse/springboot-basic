package com.programmers.springbootbasic.domain.voucher.domain.entity;


import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherType;
import java.util.UUID;

public class Voucher {
    private UUID id;
    private VoucherType voucherType;
    private Integer benefitValue;

    public Voucher(UUID id, VoucherType voucherType, Integer benefitValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    public Voucher(VoucherType voucherType, Integer benefitValue) {
        this.id = UUID.randomUUID();
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    public Voucher() {
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }

}
