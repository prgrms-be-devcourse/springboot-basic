package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.enums.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount(){
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public VoucherType getVoucherType(){ return VoucherType.DISCOUNT; };

    @Override
    public String toString() {
        return "Percent Discount Voucher [ " +
                "voucherId = " + voucherId +
                ", percent = " + percent +
                " ]";
    }

}