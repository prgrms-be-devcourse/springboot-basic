package org.prgrms.kdt.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Voucher {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID voucherId;
    @Enumerated(EnumType.STRING)
    private VoucherType voucherType;
    private double discountAmount;

    public Voucher(VoucherType voucherType, double discountAmount) {
        this.voucherId = UUID.randomUUID();
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public Voucher(UUID voucherId, VoucherType voucherType, double discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public Voucher() {

    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
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

    @Override
    public int hashCode(){
        return Objects.hash(voucherId);
    }
}
