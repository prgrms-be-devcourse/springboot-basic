package org.prgrms.kdtspringdemo.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final  UUID voucherId;
    private  final long amount;
    public FixedAmountVoucher(UUID voucherId, long amount) throws Exception{
        if(!voucherAllow(amount)) throw new Exception("허용되지 않는 숫자입니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount-amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public String discountValue() {

        return String.valueOf(amount);
    }

    private boolean voucherAllow(Long amount){
        if(amount>=0){
            return true;
        }
        return false;
    }
}
