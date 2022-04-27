package com.blessing333.springbasic.voucher.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode(of = "voucherId")
public class Voucher {
    private final UUID voucherId;
    private VoucherType voucherType;
    private long discountAmount;

    public Voucher(UUID id, VoucherType voucherType, long discountAmount) {
        validateDiscountAmount(voucherType, discountAmount);
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
        this.voucherId = id;
    }

    public Voucher(UUID id, String stringType, long discountAmount) {
        VoucherType type = VoucherType.fromString(stringType).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 바우처 타입입니다"));
        validateDiscountAmount(type, discountAmount);
        this.discountAmount = discountAmount;
        this.voucherType = type;
        this.voucherId = id;
    }

    public long discount(long beforePrice) {
        return voucherType.discount(beforePrice, discountAmount);
    }

    private void validateDiscountAmount(VoucherType type, long discountAmount) {
        type.validateDiscountAmount(discountAmount);
    }

    public void changeVoucherType(VoucherType type){
        voucherType = type;
    }

    public void changeDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("바우처 아이디 : ").append(this.getVoucherId().toString()).append("\n")
                .append("바우처 타입 : ").append(voucherType.getDescription()).append("\n")
                .append("할인 금액(비율) : ").append(discountAmount);

        if (this.voucherType == VoucherType.FIXED)
            sb.append("원");
        else
            sb.append("%");
        return sb.toString();
    }

    @RequiredArgsConstructor
    @Getter
    public enum VoucherType {
        FIXED("1", "고정 금액 할인") {
            @Override
            public long discount(long beforePrice, long discountAmount) {
                long discountedPrice = beforePrice - discountAmount;
                return Math.max(discountedPrice, 0);
            }

            @Override
            public void validateDiscountAmount(long discountAmount) {
                if (discountAmount < 0)
                    throw new VoucherCreateFailException("할인 금액은 0보다 작을 수 없습니다");
            }

        },
        PERCENT("2", "비율 할인") {
            @Override
            public long discount(long beforePrice, long discountAmount) {
                double discountRate = (100 - discountAmount) / 100.0;
                return Math.round(beforePrice * discountRate);
            }

            @Override
            public void validateDiscountAmount(long discountPercent) {
                if (discountPercent < 1 || discountPercent > 100)
                    throw new VoucherCreateFailException("할인 비율은 0초과 100 이하로 입력해야합니다");
            }
        };

        private static final Map<String, VoucherType> validVoucherTypes = initValidVoucherTypes();
        private final String optionNumber;
        private final String description;

        public static Map<String, VoucherType> getValidVoucherTypes() {
            return validVoucherTypes;
        }

        private static Map<String, VoucherType> initValidVoucherTypes() {
            return Collections.unmodifiableMap(
                    Stream.of(VoucherType.values())
                            .collect(Collectors.toMap(VoucherType::getOptionNumber, Function.identity()))
            );
        }

        public static Optional<VoucherType> fromString(String optionNumber) {
            return Optional.ofNullable(validVoucherTypes.get(optionNumber));
        }

        public abstract long discount(long beforePrice, long discountAmount);

        public abstract void validateDiscountAmount(long discountAmount);

    }
}
