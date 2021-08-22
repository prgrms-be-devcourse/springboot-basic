package org.prgrms.kdt.Factory;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;

import java.util.UUID;

import static org.prgrms.kdt.domain.voucher.VoucherType.FIX;
import static org.prgrms.kdt.domain.voucher.VoucherType.PERCENT;

public class VoucherFactory {

    public static Voucher createVoucher(UUID id, VoucherType type, long value) {
        return switch (type) {
            case FIX -> new FixedAmountVoucher(id, value);
            case PERCENT -> new PercentAmountVoucher(id, value);
        };

    }
}
