package com.programmers.voucher.entity.voucher;

import com.programmers.voucher.view.dto.VoucherType;

import java.util.UUID;

public class Voucher {
    private UUID id;
    private VoucherType type;
    private int amount;

    private Voucher(UUID id, VoucherType type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public static Voucher create(VoucherType type, int amount) {
        return new Voucher(UUID.randomUUID(), type, amount);
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
