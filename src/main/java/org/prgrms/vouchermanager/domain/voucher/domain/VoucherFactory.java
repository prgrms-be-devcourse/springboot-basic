package org.prgrms.vouchermanager.domain.voucher.domain;

import java.text.MessageFormat;
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

        throw new IllegalArgumentException(MessageFormat.format("{0} 타입의 바우처가 존재하지 않습니다.", type));
    }
}
