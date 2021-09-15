package org.prgrms.kdt.voucher;

import org.prgrms.kdt.VoucherType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher  implements Voucher,Serializable {
    private final long discountAmount;
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt){
        this.discountAmount = amount;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount- discountAmount;
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public String toString(){
        return discountAmount +" fixed amount voucher";
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FIXED;
    }

    @Override
    public long getDiscount() {
        return discountAmount;
    }
}
