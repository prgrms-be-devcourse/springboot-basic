package org.prgrms.vouchermanager.voucher.domain;

import java.util.UUID;

/**
 * VoucherType에 따라 voucher객체를 반환하는 팩토리
 */
public class VoucherFactory {

    public static Voucher getVoucher(UUID voucherId, VoucherType type, long amount) {
        switch (type) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, amount);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, amount);
            }
        }

        throw new IllegalArgumentException();
    }
}
