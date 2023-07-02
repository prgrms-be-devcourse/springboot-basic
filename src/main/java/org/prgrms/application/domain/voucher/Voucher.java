package org.prgrms.application.domain.voucher;


public abstract class Voucher {

    protected Long voucherId;
    protected VoucherType voucherType;

    public abstract Long getVoucherId();

    public abstract VoucherType getVoucherType();

    public abstract double discount(double beforeDiscount);

}
