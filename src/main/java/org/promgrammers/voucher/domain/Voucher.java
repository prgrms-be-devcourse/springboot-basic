package org.promgrammers.voucher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class Voucher {
    private final long amount;
    private final UUID id;


    public abstract VoucherType getVoucherType();

    public abstract long calculateDiscount(long price);
}