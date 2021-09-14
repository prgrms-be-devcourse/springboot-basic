package org.prgrms.kdt.engine.voucher;

import org.prgrms.kdt.application.CommandType;
import org.prgrms.kdt.engine.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.engine.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.engine.voucher.domain.Voucher;

import java.util.UUID;

public enum VoucherType {
    FIXED {
        @Override
        public Voucher createVoucher(long amount) {
            return new FixedAmountVoucher(UUID.randomUUID(), amount);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, long amount) {
            return new FixedAmountVoucher(voucherId, amount);
        }
    },
    PERCENT {
        @Override
        public Voucher createVoucher(long percent) {
            return new PercentDiscountVoucher(UUID.randomUUID(), percent);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, long percent) {
            return new PercentDiscountVoucher(voucherId, percent);
        }
    };

    public abstract Voucher createVoucher(long rate);
    public abstract Voucher createVoucher(UUID voucherId, long rate);

    public static boolean has(String typeString) {
        for(VoucherType type : VoucherType.values()) {
            if (type.name().equals(typeString.toUpperCase()))
                return true;
        }
        return false;
    }
}
