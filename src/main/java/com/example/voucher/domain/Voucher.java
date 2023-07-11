package com.example.voucher.domain;

import static com.example.voucher.constant.ExceptionMessage.*;
import java.util.Arrays;
import java.util.UUID;

import com.example.voucher.domain.dto.VoucherDTO;

public class Voucher {

    public enum Type {
        FIXED_AMOUNT_DISCOUNT(1),
        PERCENT_DISCOUNT(2);

        private final int inputNum;

        Type(int inputNum) {
            this.inputNum = inputNum;
        }

        public int getInputNum() {
            return inputNum;
        }

        public static Type getType(int inputVoucherType) {
            return Arrays.stream(values())
                .filter(v -> inputVoucherType == v.getInputNum())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ARGUMENT_CANT_CREATE_VOUCHER));
        }
    }

    private final UUID voucherId;
    private final Type type;
    private final long discountValue;
    private final DiscountPolicy discountPolicy;

    public Voucher(Type voucherType, long discountValue) {
        validateDiscountValue(voucherType, discountValue);
        this.voucherId = UUID.randomUUID();
        this.type = voucherType;
        this.discountValue = discountValue;
        this.discountPolicy = new DiscountPolicy();
    }

    public Voucher(UUID voucherId, Type type, long discountValue) {
        this.voucherId = voucherId;
        this.type = type;
        this.discountValue = discountValue;
        this.discountPolicy = new DiscountPolicy();
    }

    private void validateDiscountValue(Voucher.Type voucherType, Long discountValue) {
        switch (voucherType) {
            case FIXED_AMOUNT_DISCOUNT -> validatePositive(discountValue);
            case PERCENT_DISCOUNT -> validatePercent(discountValue);
        }
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

    private void validatePercent(long percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException(MESSAGE_ERROR_RANGE_CONSTRAINT);
        }
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public Type getType() {
        return type;
    }

    public Long discount(Long originalAmount) {
        return discountPolicy.discount(type, originalAmount, discountValue);
    }

    public VoucherDTO toDTO(){
        return new VoucherDTO(voucherId,discountValue,type);
    }

}
