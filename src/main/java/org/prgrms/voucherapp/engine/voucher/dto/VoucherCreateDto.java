package org.prgrms.voucherapp.engine.voucher.dto;

import org.prgrms.voucherapp.exception.UnknownVoucherTypeException;
import org.prgrms.voucherapp.exception.WrongAmountException;
import org.prgrms.voucherapp.global.enums.VoucherType;

import java.util.UUID;

public record VoucherCreateDto(
        VoucherType type,
        long amount
) {
    public VoucherCreateDto {
        if (amount > type.getMaxDiscountAmount()) throw new WrongAmountException("최대 할인 금액을 초과하였습니다.");
    }
}
