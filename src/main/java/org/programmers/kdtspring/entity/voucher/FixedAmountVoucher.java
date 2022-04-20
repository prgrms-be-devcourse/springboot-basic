package org.programmers.kdtspring.entity.voucher;

public class FixedAmountVoucher extends Voucher {


    private final int amount;
    private final String voucherType;

    public FixedAmountVoucher(Long voucherId, Long customerId, int amount, String voucherType) {
        super(voucherId, customerId);
        this.amount = amount;
        this.voucherType = voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public int getDiscount() {
        return this.amount;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }
}
