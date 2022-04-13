package org.prgrms.voucherapplication.entity;

import java.util.UUID;

public interface Voucher {

    /**
     * VoucherId 반환
     *
     * @return: VoucherId
     */
    UUID getVoucherId();

    /**
     * 할인가 또는 할인율 반환
     *
     * @return: discount amount 또는 percent
     */
    long getDiscountValue();

    /**
     * 엔티티의 정보를 string으로 반환
     *
     * @return: String
     */
    String toString();
}
