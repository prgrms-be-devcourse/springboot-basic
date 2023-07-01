package com.prgms.VoucherApp.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Long DISCOUNT_PERCENTAGE_MAX_VALUE = 100L;
    private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private final UUID voucherId;
    private final BigDecimal percent;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, BigDecimal percent, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
    }

    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        return calculateAmount(beforeAmount);
    }

    private BigDecimal calculateAmount(BigDecimal beforeAmount) {
        BigDecimal discountAmount;
        try {
            discountAmount = beforeAmount.multiply(percent)
                    .divide(BigDecimal.valueOf(DISCOUNT_PERCENTAGE_MAX_VALUE), MathContext.UNLIMITED);
        } catch (ArithmeticException e) {
            log.warn("무한 소수 예외가 발생하였습니다. 20번째 자리에서 반올림합니다.");
            discountAmount = beforeAmount.multiply(percent)
                    .divide(BigDecimal.valueOf(DISCOUNT_PERCENTAGE_MAX_VALUE), 20, RoundingMode.HALF_UP);
        }
        return beforeAmount.subtract(discountAmount);
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return this.percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
