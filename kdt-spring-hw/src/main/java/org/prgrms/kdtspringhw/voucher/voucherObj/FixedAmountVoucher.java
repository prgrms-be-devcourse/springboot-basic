package org.prgrms.kdtspringhw.voucher.voucherObj;


import java.util.UUID;


public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
    }
    @Override
    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }
    @Override
    public UUID getVoucherId(){
        return voucherId;
    }
    @Override
    public long getData() {
        return amount;
    }
    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
    @Override
    public VoucherType getType(){
        return this.voucherType;
    }
}
