package org.prgrms.kdt.voucher;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(VoucherType type, long amount) {
        return switch (type) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PERCENTAGE -> new PercentDiscountVoucher(UUID.randomUUID(), amount);
            default -> throw new NoSuchElementException("존재하지 않는 Voucher 형식입니다.");
        };
    }

}
