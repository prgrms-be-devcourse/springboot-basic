package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.exception.WrongVoucherParamsException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum VoucherType {
    FiXED("fixed") {
        @Override
        public boolean isValidAmount(Float amount) {
            return amount > 0;
        }

        @Override
        public Float discount(Integer currentPrice, Float amount) {
            return (currentPrice - amount);
        }
    },
    PERCENT("percent") {
        @Override
        public boolean isValidAmount(Float percent) {
            return (percent > 0)  && (percent <= 100);
        }

        @Override
        public Float discount(Integer currentPrice, Float percent) {
            return currentPrice * (1 - percent);
        }
    };

    private final String type;
    private static final Map<String, VoucherType> vouchertypeMap = Stream.of(values()).collect(toMap(Objects::toString, e -> e));

    VoucherType(String type) {
        this.type = type;
    }

    public Float discount(Integer currentPrice, Float amount) {
        return Float.valueOf(0);
    }

    public boolean isValidAmount(Float amount) {return true;}

    @Override
    public String toString() {
        return type;
    }

    public static VoucherType fromString(String type) throws WrongVoucherParamsException {
        return Optional.ofNullable(vouchertypeMap.get(type)).orElseThrow(() -> new WrongVoucherParamsException());
    }
}
