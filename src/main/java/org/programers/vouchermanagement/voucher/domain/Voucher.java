package org.programers.vouchermanagement.voucher.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Voucher {

    private final UUID id;
    private final VoucherPolicy policy;

    @Autowired
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
