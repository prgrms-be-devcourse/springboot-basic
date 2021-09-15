package org.prgrms.kdt.voucher.domain.vo;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Arrays;

public enum Type {
    FIXED("Fixed") {
        @Override
        public long discount(long price, long value) {
            long discountAmount = price * (value / PERCENT_RATIO);
            if (discountAmount < 0) {
                throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
            }
            return discountAmount;
        }
    },
    PERCENT("Percent") {
        @Override
        public long discount(long price, long value) {
            long discountAmount = price - value;
            if (discountAmount < MIN_COUNT) {
                throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
            }
            return discountAmount;
        }
    };

    private static final int PERCENT_RATIO = 100;
    private static final int MIN_COUNT = 0;

    private final String type;

    Type(final String type) {
        this.type = type;
    }

    public static Type findByVoucherType(String inputType) {
        return Arrays.stream(Type.values())
                .filter(b -> b.type.equals(inputType))
                .findAny()
                .orElseThrow(() -> new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE));
    }

    public abstract long discount(long price, long value);
}
