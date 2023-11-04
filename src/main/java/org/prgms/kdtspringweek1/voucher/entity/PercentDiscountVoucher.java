package org.prgms.kdtspringweek1.voucher.entity;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountValue;
    private static final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    public static PercentDiscountVoucher createWithPercent(long percent) {
        return new PercentDiscountVoucher(percent);
    }

    public static PercentDiscountVoucher createWithIdAndPercent(UUID voucherId, long percent) {
        return new PercentDiscountVoucher(voucherId, percent);
    }

    private PercentDiscountVoucher(long discountValue) {
        if (discountValue > 0 && discountValue <= 100) {
            this.voucherId = UUID.randomUUID();
            this.discountValue = discountValue;
        } else {
            logger.debug("Fail to create {} -> {} percent", VoucherType.PERCENT_DISCOUNT.getName(), discountValue);
            throw new IllegalArgumentException(InputExceptionCode.INVALID_PERCENT_DISCOUNT.getMessage());
        }
    }

    private PercentDiscountVoucher(UUID voucherId, long discountValue) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + discountValue +
                '}';
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscountValue() {
        return discountValue;
    }
}
