package org.prgms.kdtspringweek1.controller.dto;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateVoucherRequestDto {
    private final long discountValue;
    private final static Logger logger = LoggerFactory.getLogger(CreateVoucherRequestDto.class);

    public CreateVoucherRequestDto(long discountValue) {
        if (discountValue > 0) {
            this.discountValue = discountValue;
        } else {
            logger.debug("Invalid discountValue -> {}", discountValue);
            throw new IllegalArgumentException(InputExceptionCode.INVALID_DISCOUNT_VALUE.getMessage());
        }
    }

    public Voucher toFixedAmountVoucher() {
        return FixedAmountVoucher.createWithAmount(discountValue);
    }

    public Voucher toPercentDiscountVoucher() {
        return PercentDiscountVoucher.createWithPercent(discountValue);
    }

    public long getDiscountValue() {
        return discountValue;
    }
}
