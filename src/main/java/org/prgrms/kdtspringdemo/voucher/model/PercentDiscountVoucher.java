package org.prgrms.kdtspringdemo.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final  UUID voucherId;

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) throws Exception{
        if(!voucherAllow(percent)) {
            throw new Exception("허용되지 않는 숫자입니다.");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }
    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount *(percent/100);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public String discountValue() {
        return String.valueOf(percent);
    }

    private boolean voucherAllow(Long value) {
        if(value>=0 && value <= 100){
            return true;
        }
        return false;
    }
}
