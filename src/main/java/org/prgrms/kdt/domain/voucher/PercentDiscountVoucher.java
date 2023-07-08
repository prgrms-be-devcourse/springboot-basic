package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherType;

public class PercentDiscountVoucher implements Voucher {
    private Long voucherId;
    private final long percent;
    private boolean status = false;

    public PercentDiscountVoucher(Long voucherId, long percent) {
        validate(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private static void validate(long percent) {
        if (percent < 0) throw new IllegalArgumentException("할인 퍼센트는 양수여야 합니다.");
        if (percent == 0) throw new IllegalArgumentException("할인 퍼센트는 0이상이어야 합니다.");
        if (percent > 100) throw new IllegalArgumentException("할인 퍼센트는 100 미만이어야 합니다.");
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public Long getDiscountAmount() {
        return percent;
    }

    @Override
    public boolean getStatus() {
        return status;
    }
}
