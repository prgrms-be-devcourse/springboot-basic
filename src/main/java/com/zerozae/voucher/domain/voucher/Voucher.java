package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ErrorMessage;

import java.util.UUID;


public abstract class Voucher {

    private final static long ZERO = 0;

    public abstract UUID getVoucherId();

    public abstract long getDiscount();

    public abstract VoucherType getVoucherType();

    public abstract UseStatusType getUseStatusType();

    public void validateVoucherInfo(long discount) {
        if(discount < ZERO) {
            throw ErrorMessage.error("바우처 할인 정보는 음수값을 입력할 수 없습니다.");
        }
    }
}
