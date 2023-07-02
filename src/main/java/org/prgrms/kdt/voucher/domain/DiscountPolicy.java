package org.prgrms.kdt.voucher.domain;

public abstract class DiscountPolicy {
    protected final double amount;

    public DiscountPolicy(double amount) {
        this.amount = amount;
    }

    public abstract double applyDiscount(double beforeDiscount);
    public double getAmount(){
        return amount;
    }
}
