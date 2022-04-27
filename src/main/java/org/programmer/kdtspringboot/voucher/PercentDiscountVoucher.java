package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private final UUID voucherId;
    private final Long percent;
    private final VoucherType type = VoucherType.PercentDiscountVoucher;
    private final static Integer MAX_AMOUNT = 100;
    private final static Integer MIN_AMOUNT = 0;

    public PercentDiscountVoucher(UUID voucherId, Long percent) {
        validatePercent(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return (long) (beforeDiscount * (100 - percent) / 100.0);
    }

    @Override
    public Long getValue() {
        return percent;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                ", type=" + type +
                '}';
    }

    private void validatePercent(Long percent) {
        if (percent <= MIN_AMOUNT || percent > MAX_AMOUNT) {
            throw new IllegalArgumentException(MIN_AMOUNT + 1 + "~" + MAX_AMOUNT + " 값을 입력해주세요");
        }
    }

}
