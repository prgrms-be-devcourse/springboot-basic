package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.exception.WrongVoucherParamsException;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Voucher implements Serializable {
    private final UUID id;
    private VoucherType type;
    private Float discountAmount;
    private Map<String, VoucherType> vouchertypeHashMap;

    public enum VoucherType {
        FiXED("fixed") {
            @Override
            public boolean isOkayAmount(Float amount) {
                return amount > 0;
            }

            @Override
            public Float discount(Integer currentPrice, Float amount) {
                return (currentPrice - amount);
            }
        },
        PERCENT("percent") {
            @Override
            public boolean isOkayAmount(Float percent) {
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

        public boolean isOkayAmount(Float amount) {return true;}

        @Override
        public String toString() {
            return type;
        }

        public static VoucherType fromString(String type) {
            return Optional.ofNullable(vouchertypeMap.get(type)).get();
        }
    };

    public Voucher(String type, Float discountAmount) throws WrongVoucherParamsException {
        this.id = UUID.randomUUID();
        this.type = VoucherType.fromString(type);
        if (this.type.isOkayAmount(discountAmount)) {
            this.discountAmount = discountAmount;
        }
        else {
            throw new WrongVoucherParamsException();
        }
    }

    @Override
    public String toString() {
        return "id: " + id + "\ntype: " + type.toString() + "\namount: " + discountAmount.toString() +"\n" ;
    }

    public Float discount(Integer currentPrice) {
        return this.type.discount(currentPrice, discountAmount);
    }

    public UUID getId() {
        return id;
    }
}
