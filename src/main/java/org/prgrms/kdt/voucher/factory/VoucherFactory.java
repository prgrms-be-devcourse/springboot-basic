package org.prgrms.kdt.voucher.factory;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;

import java.util.UUID;

/**
 * Factory Pattern
 * - Factory Pattern은 인스턴스를 만드는 절차를 추상화하는 패턴.
 * - Factory Pattern 은 SingleTon으로
 * - 싱글톤패턴은 private constructor 사용.
 */
public class VoucherFactory {

    private static VoucherFactory uniqueInstance = new VoucherFactory();

    private VoucherFactory() {
    }

    public static VoucherFactory getInstance() {
        return uniqueInstance;
    }

    public Voucher createVoucher(VoucherType type, UUID voucherId, long policyValue) {
        switch (type) {
            case PERCENTAGE:
                return new PercentDiscountVoucher(voucherId, policyValue);
            case FIXED:
                return new FixedAmountVoucher(voucherId, policyValue);
            default:
                throw new IllegalArgumentException();
        }
    }
}
