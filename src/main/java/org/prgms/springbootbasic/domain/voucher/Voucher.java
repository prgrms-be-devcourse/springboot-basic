package org.prgms.springbootbasic.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.exception.OutOfRangeException;

import java.util.UUID;

@Slf4j
public class Voucher {
    private final UUID voucherId;
    private final long discountDegree;
    private final VoucherPolicy voucherPolicy;

    public Voucher(UUID voucherId, long discountDegree, VoucherPolicy voucherPolicy) {
        if (voucherPolicy instanceof PercentDiscountPolicy) {
            if (discountDegree <= 0 || discountDegree > 100) {
                log.error("percent value is out of range.");
                throw new OutOfRangeException("percent value is out of range.");
            }
        }

        this.voucherId = voucherId;
        this.discountDegree = discountDegree;
        this.voucherPolicy = voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountDegree() {
        return discountDegree;
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public long discount(long beforeDiscount){
        return voucherPolicy.discount(beforeDiscount, this.discountDegree);
    }
}
