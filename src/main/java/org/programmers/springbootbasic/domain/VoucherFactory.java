package org.programmers.springbootbasic.domain;

import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.exception.WrongInputException;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Voucher가 추가될 경우 case 문에 추가해서 처리 가능.
 */
@Component
public class VoucherFactory{
    public Voucher getVoucher(VoucherType type, long amount) throws WrongInputException {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(), amount);
            default -> throw new WrongInputException();
        };
    }
}
