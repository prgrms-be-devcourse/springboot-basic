package com.prgrms.vouchermanager.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class FixedAmountVoucher implements Voucher {

    private final UUID id = UUID.randomUUID();
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
    public String toString() {
        return """
                Id : %s
                Discount Type : %s
                Discount amount : %d
                """.formatted(id, "fixed discount", amount);
    }
}
