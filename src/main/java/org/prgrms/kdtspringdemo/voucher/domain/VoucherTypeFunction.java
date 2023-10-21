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
            Voucher voucher = null;
            try{
                voucher = new FixedAmountVoucher(voucherId, amount, "fixedAmount");
            } catch (RuntimeException e) {
                logger.info("할인 가격은 할인 이전 값보다 작아야 합니다.");
            }
            return voucher;
        }
    },
    PERCENT_DISCOUNT_VOUCHER("percentDiscount", "비율 할인 바우처") {
        @Override
        public Voucher create(UUID voucherId, long amount) {
            Voucher voucher = null;
            try {
                voucher = new PercentDiscountVoucher(voucherId, amount, "percentDiscount");
            } catch(RuntimeException e) {
                logger.info("할인률은 1~100까지만 입력 가능합니다.");
            }
            return voucher;
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
