package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.utils.VoucherStatus;
import org.prgrms.kdt.utils.VoucherType;

public class PercentDiscountVoucher implements Voucher {
    private Long voucherId;
    private final long percent;
    private String status = VoucherStatus.AVAILABLE.toString();

    public PercentDiscountVoucher(Long voucherId, long percent) {
        validate(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    private static void validate(long percent) {
        if (percent < 0) throw new VoucherException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        if (percent == 0) throw new VoucherException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        if (percent > 100) throw new VoucherException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
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
    public String getStatus() {
        return status;
    }
}
