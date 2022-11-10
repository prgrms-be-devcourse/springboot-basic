package org.prgrms.kdt.domain;

import org.prgrms.kdt.service.dto.CreateVoucherDto;

import java.util.UUID;

public class Voucher {

    private UUID voucherId;
    private VoucherType voucherType;
    private double discountAmount;

    public Voucher(CreateVoucherDto createVoucherDto) {
        this.voucherId = UUID.randomUUID();
        this.voucherType = createVoucherDto.getVoucherType();
        this.discountAmount = createVoucherDto.getDiscountAmount();
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String toString() {
        return "<ID : " + voucherId + " , VoucherType : " + voucherType + " , DiscountAmount : " + discountAmount + " >";
    }

    @Override
    public boolean equals(Object v) {
        if (!(v instanceof Voucher)) return false;
        return this.voucherId == ((Voucher) v).getVoucherId();
    }
}
