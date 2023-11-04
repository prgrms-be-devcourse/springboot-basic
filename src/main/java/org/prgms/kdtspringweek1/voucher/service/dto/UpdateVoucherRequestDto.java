package org.prgms.kdtspringweek1.voucher.service.dto;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UpdateVoucherRequestDto {
    private final UUID voucherId;
    private final long discountValue;
    private final static Logger logger = LoggerFactory.getLogger(UpdateVoucherRequestDto.class);

    public UpdateVoucherRequestDto(UUID voucherId, long discountValue) {
        if (discountValue > 0) {
            this.voucherId = voucherId;
            this.discountValue = discountValue;
        } else {
            logger.debug("Invalid discountValue -> {}", discountValue);
            throw new IllegalArgumentException(InputExceptionCode.INVALID_DISCOUNT_VALUE.getMessage());
        }
    }

    public Voucher toFixedAmountVoucher() {
        return FixedAmountVoucher.createWithIdAndAmount(voucherId, discountValue);
    }

    public Voucher toPercentDiscountVoucher() {
        return PercentDiscountVoucher.createWithIdAndPercent(voucherId, discountValue);
    }

}
