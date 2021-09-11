package org.programmers.kdt.voucher.factory;

import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.PercentDiscountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(VoucherType type, UUID voucherId, long discount) {
        switch (type) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, discount);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, discount);
            }
            default -> {
                throw new RuntimeException(MessageFormat.format("INVALID Voucher type :{}", type));
            }
        }
    }
}
