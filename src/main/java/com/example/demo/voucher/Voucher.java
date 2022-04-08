package com.example.demo.voucher;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Voucher {
    private final UUID id;
    private VoucherType type;
    private Float discountAmount;
    private HashMap<String, VoucherType> vouchertypeHashMap;

    public enum VoucherType {
        FiXED("fixed") {
            @Override
            public Float discount(Integer currentPrice, Float amount) {
                return (currentPrice - amount);
            }
        },
        PERCENT("percent") {
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

        @Override
        public String toString() {
            return type;
        }

        public static Optional<VoucherType> fromString(String type) {
            return Optional.ofNullable(vouchertypeMap.get(type));
        }
    };

    public Voucher(String type, Float discountAmount) {
        this.id = UUID.randomUUID();
        this.type = VoucherType.fromString(type).get();
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "id: " + id + "\ntype: " + type.toString() + "\namount: " + discountAmount.toString() ;
    }

    public Float discount(Integer currentPrice) {
        return this.type.discount(currentPrice, discountAmount);
    }

    public UUID getId() {
        return id;
    }
}
