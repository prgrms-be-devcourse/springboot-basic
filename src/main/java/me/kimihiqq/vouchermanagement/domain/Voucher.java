package me.kimihiqq.vouchermanagement.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    String getType();

    long discount(long beforeDiscount);
}
