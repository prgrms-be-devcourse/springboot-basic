package org.programmers.springbootbasic.domain;

import org.programmers.springbootbasic.data.VoucherType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultVoucherFactory implements VoucherFactory {

    @Override
    public Voucher getVoucher(VoucherType type, long amount) {
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PERCENT -> new PercentDiscountVoucher(UUID.randomUUID(), amount);
        };
    }
}
