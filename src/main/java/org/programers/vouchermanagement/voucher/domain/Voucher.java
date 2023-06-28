package org.programers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class Voucher {

    private final UUID id;
    private final VoucherPolicy policy;
    private final VoucherType type;

    public Voucher(VoucherPolicy policy, VoucherType type) {
        this(UUID.randomUUID(), policy, type);
    }

    public Voucher(UUID id, VoucherPolicy policy, VoucherType type) {
        this.id = id;
        this.policy = policy;
        this.type = type;
    }

    public int discount(int price) {
        return policy.discount(price);
    }

    public UUID getId() {
        return id;
    }

    public VoucherPolicy getPolicy() {
        return policy;
    }

    public VoucherType getType() {
        return type;
    }
}
