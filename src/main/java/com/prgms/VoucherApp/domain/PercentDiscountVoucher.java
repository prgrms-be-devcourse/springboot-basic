package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private final UUID voucherId;
    private final BigDecimal percent;

    public PercentDiscountVoucher(UUID voucherId, BigDecimal percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        return calculateAmount(beforeAmount);
    }

    private BigDecimal calculateAmount(BigDecimal beforeAmount) {
        BigDecimal discountAmount;
        try {
            discountAmount = beforeAmount.multiply(percent).divide(BigDecimal.valueOf(100), MathContext.UNLIMITED);
        } catch (ArithmeticException e) {
            log.warn("무한 소수 예외가 발생하였습니다. 20번째 자리에서 반올림합니다.");
            discountAmount = beforeAmount.multiply(percent).divide(BigDecimal.valueOf(100), 20, RoundingMode.HALF_UP);
        }
        return beforeAmount.subtract(discountAmount);
    }

    @Override
    public UUID getUUID() {
        return this.voucherId;
    }

    @Override
    public VoucherDto convertVoucherDto() {
        String voucherId = String.valueOf(this.voucherId);
        String discountAmount = String.valueOf(this.percent);
        return new VoucherDto(voucherId, discountAmount, "percent");
    }
}
