package com.programmers.springbootbasic.domain.voucher.domain.entity;


import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherType;
import java.util.UUID;

public class Voucher {

    private final UUID id;
    private final VoucherType voucherType;
    private final Integer benefitValue;

    public Voucher(VoucherType voucherType, Integer benefitValue) {
        this(UUID.randomUUID(), voucherType, benefitValue);
    }

    public Voucher(UUID id, VoucherType voucherType, Integer benefitValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
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
