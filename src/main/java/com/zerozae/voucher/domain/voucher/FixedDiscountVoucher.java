package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ExceptionMessage;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
public class FixedDiscountVoucher extends Voucher {

    private static final long MINIMUM_DISCOUNT = 0L;

    public FixedDiscountVoucher(Long discount) {
        try {
            validateVoucherInfo(discount);
        }catch (ExceptionMessage e){
            throw ExceptionMessage.error(e.getMessage());
        }
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = UseStatusType.AVAILABLE;
        this.createdAt = LocalDateTime.now();
    }

    public FixedDiscountVoucher(UUID voucherId, long discount, UseStatusType useStatusType, LocalDateTime createdAt) {
        try {
            validateVoucherInfo(discount);
        }catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = useStatusType;
        this.createdAt = createdAt;
    }

    @Override
    public void validateVoucherInfo(long discount) {
        if(discount <= MINIMUM_DISCOUNT) {
            throw ExceptionMessage.error("고정할인 바우처의 할인 정보는 1이상의 값만을 입력할 수 없습니다.");
        }
    }
}
