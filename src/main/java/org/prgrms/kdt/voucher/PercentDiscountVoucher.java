package org.prgrms.kdt.voucher;

import org.prgrms.kdt.VoucherType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private final long percentDiscount;
    private final UUID voucherId;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt){
        this.percentDiscount = percent;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount*(percentDiscount /100);
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public String toString(){
        return percentDiscount + " percent discount voucher";
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long getDiscount() {
        return percentDiscount;
    }


}
