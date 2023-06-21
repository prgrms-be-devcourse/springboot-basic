package org.programers.vouchermanagement.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Voucher {

    private final UUID id;
    private final VoucherPolicy policy;

    public Voucher(VoucherPolicy policy) {
        this.id = UUID.randomUUID();
        this.policy = policy;
    }

    public int discount(int price) {
        return policy.discount(price);
    }
}
