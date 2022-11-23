package org.prgrms.kdt.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Voucher {

    @Id
    @Column
    private long id = 0;
    @Enumerated(EnumType.STRING)
    private VoucherType voucherType;
    private double discountAmount;

    public Voucher(VoucherType voucherType, double discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public Voucher(Long id, VoucherType voucherType, double discountAmount) {
        this.id = id;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public Voucher() {

    }

    public long getId() {
        return id;
    }

    public void setId(long voucherId) {
        this.id = voucherId;
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
        return "<ID : " + id + " , VoucherType : " + voucherType + " , DiscountAmount : " + discountAmount + " >";
    }

    @Override
    public boolean equals(Object v) {
        if (!(v instanceof Voucher)) return false;
        return this.id == ((Voucher) v).getId();
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
