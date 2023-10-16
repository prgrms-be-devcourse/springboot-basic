package org.prgrms.kdtspringdemo.voucher.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;

public enum VoucherTypeFunction {
    FIXED_AMOUNT_VOUCHER("fixedAmount", "고정 할인 바우처") {
        @Override
        public Voucher create(UUID voucherId, long amount) {
            return new FixedAmountVoucher(voucherId, amount, "fixedAmount");
        }
    },
    PERCENT_DISCOUNT_VOUCHER("percentDiscount", "비율 할인 바우처") {
        @Override
        public Voucher create(UUID voucherId, long amount) {
            return new PercentDiscountVoucher(voucherId, amount, "percentDiscount");
        }
    };

    private final String type;
    private final String description;
    private static final Logger logger = LoggerFactory.getLogger(VoucherTypeFunction.class);

    VoucherTypeFunction(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static VoucherTypeFunction findByCode(String type) {
        return Arrays.stream(values())
                .filter(option -> option.type.equals(type))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("해당 타입의 바우처가 존재하지 않습니다.");
                    return new NoSuchElementException("해당 타입의 바우처가 존재하지 않습니다.");
                });
    }

    public abstract Voucher create(UUID voucherId, long amount);

}
