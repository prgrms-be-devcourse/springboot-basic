package org.prgrms.kdtspringdemo.domain.voucher.data;

import lombok.Getter;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;

import java.util.UUID;

@Getter
public abstract class Voucher {
    private final UUID voucherId;
    private long amount;
    private UUID customerId;

    public Voucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = UUID.nameUUIDFromBytes("null".getBytes());
    }

    public Voucher(UUID voucherId, long amount, UUID customerId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = customerId;
    }

    public Voucher changeTypeAndAmount(VoucherType voucherType, int amount){
        if(voucherType==VoucherType.FIXED){
            return new FixedAmountVoucher(this.getVoucherId(), amount, this.getCustomerId());
        }
        return new PercentDiscountVoucher(this.getVoucherId(), amount,this.getCustomerId());
    }

    abstract void validateDiscountAmount(long amount);

    abstract long discount(long beforeDiscount);

    public abstract VoucherType getType();
}
