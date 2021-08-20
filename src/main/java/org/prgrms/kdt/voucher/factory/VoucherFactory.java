package org.prgrms.kdt.voucher.factory;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;

import java.util.UUID;

/**
 * Factory Pattern
 * - Factory Pattern은 인스턴스를 만드는 절차를 추사아화하는 패턴.
 * - Factory Pattern 은 SingleTon으로 -> 왜?
 * - switch
 */
public class VoucherFactory {
    public static Voucher createVoucher(VoucherType type, UUID voucherId, long policyValue) {
        if (type.equals(VoucherType.PERCENTAGE)) {
            return new PercentDiscountVoucher(voucherId, policyValue);
        } else if (type.equals(VoucherType.FIXED)) {
            return new FixedAmountVoucher(voucherId, policyValue);
        }
        return null;
    }
}
