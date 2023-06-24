package org.programers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class Voucher {

    private final UUID id;
    private final VoucherPolicy policy;

    public Voucher(VoucherPolicy policy) {
        this(UUID.randomUUID(), policy);
    }

    public Voucher(UUID id, VoucherPolicy policy) {
        this.id = id;
        this.policy = policy;
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
}
