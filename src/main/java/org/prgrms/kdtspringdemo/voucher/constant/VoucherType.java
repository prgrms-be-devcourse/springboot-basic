package org.prgrms.kdtspringdemo.voucher.constant;

import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER_TYPE;

public enum VoucherType {
    FIXED {
        @Override
        public Voucher createVoucher(long amount) {
            return new FixedAmountVoucher(amount);
        }

        @Override
        public Voucher updateVoucher(UUID voucherId, long amount) {
            return new FixedAmountVoucher(voucherId, amount);
        }
    },
    PERCENT {
        @Override
        public Voucher createVoucher(long amount) {
            return new PercentAmountVoucher(amount);
        }

        @Override
        public Voucher updateVoucher(UUID voucherId, long amount) {
            return new PercentAmountVoucher(voucherId, amount);
        }
    }
    ;

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    public abstract Voucher createVoucher(long amount);

    public abstract Voucher updateVoucher(UUID voucherId, long amount);

    public static VoucherType findVoucherType(String userVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(userVoucherType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userVoucherType, NOT_FOUND_VOUCHER_TYPE.getMessage());
                    throw new IllegalArgumentException(NOT_FOUND_VOUCHER_TYPE.getMessage());
                });
    }
}
