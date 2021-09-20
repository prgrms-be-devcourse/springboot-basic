package org.prgrms.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;
    private final VoucherType type = VoucherType.FIXED;
    private String customerEmail;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if(amount == 0) throw new IllegalArgumentException("Amount should not b zero");
        if(amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, long amount, String customerEmail) {
        if(amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if(amount == 0) throw new IllegalArgumentException("Amount should not b zero");
        if(amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerEmail = customerEmail;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherAmount() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return type;
    }

    @Override
    public void setCustomer(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String getCustomerEmail() {
        return customerEmail;
    }

    public long discount(long beforeDiscount){
        var disCountedAmount = beforeDiscount - amount;
        return (disCountedAmount < 0) ? 0 : disCountedAmount;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Voucher Id : " + voucherId + " ");
        sb.append("Amount : " + amount + " ");
        sb.append("Type : " + type);
        return sb.toString();
    }
}
