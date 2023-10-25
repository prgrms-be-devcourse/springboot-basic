package org.prgms.kdtspringweek1.voucher.entity;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final static VoucherType voucherType = VoucherType.FIXED_AMOUNT;
    private final static Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public static FixedAmountVoucher createWithAmount(long amount) {
        return new FixedAmountVoucher(amount);
    }

    public static FixedAmountVoucher createWithIdAndAmount(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    private FixedAmountVoucher(long amount) {
        if (amount > 0) {
            this.voucherId = UUID.randomUUID();
            this.amount = amount;
        } else {
            logger.debug("Fail to create {} -> {} amount", VoucherType.FIXED_AMOUNT.getName(), amount);
            throw new IllegalArgumentException(InputExceptionCode.INVALID_FIXED_AMOUNT.getMessage());
        }
    }

    private FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }
}
