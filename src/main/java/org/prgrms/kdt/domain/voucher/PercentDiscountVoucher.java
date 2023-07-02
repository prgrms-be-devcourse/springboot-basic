package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.domain.voucher.Voucher;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validate(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private static void validate(long percent) {
        if (percent < 0) throw new IllegalArgumentException("할인 퍼센트는 양수여야 합니다.");
        if (percent > 100) throw new IllegalArgumentException("할인 퍼센트는 100 미만이어야 합니다.");
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT_VOUCHER;
    }

    @Override
    public Long getDiscountAmount() {
        return percent;
    }
}
