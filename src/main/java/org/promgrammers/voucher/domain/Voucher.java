package org.promgrammers.voucher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class Voucher {
    private final long amount;
    private final UUID id;


    public abstract VoucherType getVoucherType();

    public abstract long calculateDiscount(long price);

    @Override
    public String toString() {
        return "Voucher{" +
                ", id=" + id +
                "amount=" + amount +
                ", voucherType=" + getVoucherType() +
                '}';
    }
}