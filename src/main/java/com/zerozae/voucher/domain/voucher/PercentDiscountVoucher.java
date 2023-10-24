package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import lombok.Getter;

import java.util.UUID;


@Getter
public class PercentDiscountVoucher extends Voucher {

    private static final long ZERO = 0L;
    private static final long MAXIMUM = 100L;

    public PercentDiscountVoucher(Long discount) {
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = UseStatusType.AVAILABLE;
    }

    public PercentDiscountVoucher(UUID voucherId, Long discount, UseStatusType useStatusType){
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = useStatusType;
    }

    @Override
    public void validateVoucherInfo(long discount) {
        if(discount < ZERO || discount > MAXIMUM) {
            throw ErrorMessage.error("비율할인 바우처의 할인 정보는 1 ~ 100 값만 입력해야 합니다.");
        }
    }
}
