package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ErrorMessage;
import lombok.Getter;

import java.util.UUID;


@Getter
public class FixedDiscountVoucher extends Voucher {

    private static final long ZERO = 0L;

    public FixedDiscountVoucher(Long discount) {
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = UseStatusType.AVAILABLE;
    }

    public FixedDiscountVoucher(UUID voucherId, long discount, UseStatusType useStatusType){
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = useStatusType;
    }

    @Override
    public void validateVoucherInfo(long discount) {
        if(discount < ZERO) {
            throw ErrorMessage.error("고정할인 바우처의 할인 정보는 음수값을 입력할 수 없습니다.");
        }
    }
}
