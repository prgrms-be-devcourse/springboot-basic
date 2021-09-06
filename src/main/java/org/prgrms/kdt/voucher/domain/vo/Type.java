package org.prgrms.kdt.voucher.domain.vo;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Arrays;

public enum Type {
    FIXED("Fixed") {
        @Override
        public long discount(long price, long value) {
            long discountAmount = price * (value / 100);
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
            if (discountAmount < 0) {
                throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
            }
            return discountAmount;
        }
    };

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
