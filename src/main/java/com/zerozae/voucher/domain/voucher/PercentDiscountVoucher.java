package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ExceptionMessage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
public class PercentDiscountVoucher extends Voucher {

    private static final long MINIMUM_DISCOUNT = 0L;
    private static final long MAXIMUM_DISCOUNT = 100L;

    public PercentDiscountVoucher(Long discount) {
        try {
            validateVoucherInfo(discount);
        }catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = UseStatusType.AVAILABLE;
        this.createdAt = LocalDateTime.now();
    }

    public PercentDiscountVoucher(UUID voucherId, long discount, UseStatusType useStatusType, LocalDateTime createdAt) {
        try {
            validateVoucherInfo(discount);
        }catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = useStatusType;
        this.createdAt = createdAt;
    }

    @Override
    public void validateVoucherInfo(long discount) {
        if(discount <= MINIMUM_DISCOUNT || discount > MAXIMUM_DISCOUNT) {
            throw ExceptionMessage.error("비율할인 바우처의 할인 정보는 1 ~ 100 값만 입력해야 합니다.");
        }
    }
}
