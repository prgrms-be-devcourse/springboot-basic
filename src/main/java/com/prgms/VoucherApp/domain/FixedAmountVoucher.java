package com.prgms.VoucherApp.domain;

import com.prgms.VoucherApp.dto.VoucherDto;

import java.math.BigDecimal;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final BigDecimal fixedAmount;

    public FixedAmountVoucher(UUID voucherId, BigDecimal fixedAmount) {
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
    }

    @Override
    public BigDecimal discount(BigDecimal beforeAmount) {
        if (isResultNegative(beforeAmount)) {
            return BigDecimal.ZERO;
        }

        return beforeAmount.subtract(fixedAmount);
    }

    @Override
    public UUID getUUID() {
        return this.voucherId;
    }

    @Override
    public VoucherDto convertVoucherDto() {
        String voucherId = String.valueOf(this.voucherId);
        String discountAmount = String.valueOf(this.fixedAmount);
        return new VoucherDto(voucherId, discountAmount, "fix");
    }

    private boolean isResultNegative(BigDecimal beforeAmount) {
        return beforeAmount.subtract(fixedAmount)
                .compareTo(BigDecimal.ZERO) < 0;
    }
}
