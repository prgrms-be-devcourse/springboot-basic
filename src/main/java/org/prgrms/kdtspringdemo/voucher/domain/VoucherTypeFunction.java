package org.prgrms.kdtspringdemo.voucher.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.UUID;

public enum VoucherTypeFunction {
    FIXED_DISCOUNT_POLICY("fixeddiscount", "고정 할인 바우처") {
        @Override
        public Voucher create(UUID voucherId, long amount) {
            VoucherPolicy voucherPolicy = new FixedDiscountPolicy(amount);
            return new Voucher(voucherId, voucherPolicy);
        }
    },
    PERCENT_DISCOUNT_POLICY("percentdiscount", "비율 할인 바우처") {
        @Override
        public Voucher create(UUID voucherId, long amount) {
            VoucherPolicy voucherPolicy = new PercentDiscountPolicy(amount);
            return new Voucher(voucherId, voucherPolicy);
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
        String lowerType = type.toLowerCase();
        return Arrays.stream(values())
                .filter(option -> option.type.equals(lowerType))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("해당 타입의 바우처가 존재하지 않습니다.");
                    return new NoSuchElementException("해당 타입의 바우처가 존재하지 않습니다.");
                });
    }

    public abstract Voucher create(UUID voucherId, long amount);

}
