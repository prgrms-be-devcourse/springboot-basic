package org.prgms.springbootbasic.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.exception.OutOfRangeException;

import java.util.UUID;

@Slf4j
public class PercentDiscountVoucher implements VoucherPolicy {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent > 100 || percent <= 0) {
            log.warn("percent value is out of range.");
            throw new OutOfRangeException("percent value is out of range.");
        }

        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return this.percent;
    }

    @Override
    public long discount(long beforeDiscount) { // 음수 고려
        if (beforeDiscount < 0)
            throw new OutOfRangeException("beforeDiscount is less than 0.");
        return beforeDiscount * percent / 100L;
    }
}
