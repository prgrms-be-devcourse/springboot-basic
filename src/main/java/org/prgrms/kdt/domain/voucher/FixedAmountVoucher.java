package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.utils.VoucherStatus;
import org.prgrms.kdt.utils.VoucherType;

public class FixedAmountVoucher implements Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final Long voucherId;
    private final long amount;
    private String status = VoucherStatus.AVAILABLE.toString();

    public FixedAmountVoucher(Long voucherId, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    private static void validate(long amount) {
        if (amount < 0) throw new VoucherException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        if (amount == 0) throw new VoucherException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new VoucherException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public Long getDiscountAmount() {
        return amount;
    }

    @Override
    public String getStatus() {
        return status;
    }

}
