package com.programmers.voucher.entity.voucher;

import java.util.UUID;

public class Voucher {
    private final UUID id;
    private VoucherType type;
    private int amount;

    public Voucher(UUID id, VoucherType type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Voucher create(VoucherType type, DiscountAmount discountAmount) {
        return new Voucher(UUID.randomUUID(), type, discountAmount.getAmount());
    }

    public void update(VoucherType type, DiscountAmount discountAmount) {
        this.type = type;
        this.amount = discountAmount.getAmount();
    }

    public long discount(long price) {
        return type.discount(price, amount);
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
