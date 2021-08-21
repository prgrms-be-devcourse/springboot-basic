package org.prgrms.kdt.Factory;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;

import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(UUID id, VoucherType type, long value) {
        if (type == VoucherType.FIX) {
            return new FixedAmountVoucher(id, value);
        } else {
            return new PercentAmountVoucher(id, value);
        }
    }
}
