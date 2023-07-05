package org.prgrms.application.domain.voucher;


public abstract class Voucher {

    protected Long voucherId;
    protected double discountAmount;

    public abstract Long getVoucherId();

    public abstract VoucherType getVoucherType();

    public abstract double getDiscountAmount();

    public abstract double discount(double beforeDiscount);

}
