package com.programmers.voucher.entity.voucher;

import java.util.function.BiConsumer;

public enum UpdatableField {
    NAME((voucher, input) -> {
        if(input.isBlank()) throw new IllegalArgumentException("Voucher name cannot be empty.");
        voucher.updateName(input);
    }),
    TYPE((voucher, input) -> {
        final DiscountType newType = DiscountType.of(input);
        voucher.getDiscountPolicy().updateType(newType);
    }),
    VALUE((voucher, input) -> {
        int newValue = Integer.parseInt(input);
        voucher.getDiscountPolicy().updateAmount(newValue);
    }),
    CUSTOMER((voucher, input) -> {
        final long newCustomer = Long.parseLong(input);
        voucher.updateCustomerId(newCustomer);
    }),
    UNKNOWN((voucher, input) -> {
        throw new IllegalArgumentException("Unknown update field.");
    });

    private final BiConsumer<Voucher, String> behavior;

    public void update(Voucher voucher, String value) {
        this.behavior.accept(voucher, value);
    }

    UpdatableField(BiConsumer<Voucher, String> behavior) {
        this.behavior = behavior;
    }

    public static UpdatableField of(String input) {
        try {
            return UpdatableField.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
