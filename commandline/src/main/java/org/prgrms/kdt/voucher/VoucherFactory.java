package org.prgrms.kdt.voucher;

import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.NotFindVoucherType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(String type, long amount) {
        VoucherType voucherType = VoucherType.selectVoucherType(type);
        return switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PERCENTAGE -> new PercentDiscountVoucher(UUID.randomUUID(), amount);
            default -> throw new NotFindVoucherType();
        };
    }

}
