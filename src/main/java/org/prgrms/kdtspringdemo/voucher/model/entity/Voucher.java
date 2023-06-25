package org.prgrms.kdtspringdemo.voucher.model.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long executeDiscount(long beforeDiscount);

    long validateDiscount(long discount);

    String toString();
}
