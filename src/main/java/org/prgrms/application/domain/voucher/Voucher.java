package org.prgrms.application.domain.voucher;


public abstract class Voucher {
    protected static int MIN_DISCOUNT_VALUE = 0;
    protected Long voucherId;
    protected double discountAmount;

    public Voucher(Long voucherId, double discountAmount){
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    public Long getVoucherId(){
        return voucherId;
    }

    public double getDiscountAmount(){
        return discountAmount;
    }

    public abstract VoucherType getVoucherType();

    public abstract double discount(double beforeDiscount);

}
