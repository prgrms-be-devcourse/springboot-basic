package org.promgrammers.voucher.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
abstract class Voucher {
    private final long amount;
    private final UUID id;


    abstract VoucherType getVoucherType();

    abstract long calculateDiscount(long price);
}