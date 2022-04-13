package org.prgrms.voucherapplication.entity;

import java.util.UUID;

public interface Voucher {

    /**
     * VoucherId 반환
     * @return: VoucherId
     */
    UUID getVoucherId();

    long getDiscountValue();

    String toString();
}
