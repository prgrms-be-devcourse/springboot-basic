package org.prgrms.application.domain.voucher;


public abstract class Voucher {
    protected Long voucherId;
    protected double amount;

    public abstract Long getVoucherId();

    public abstract double discount(double beforeDiscount);

}
