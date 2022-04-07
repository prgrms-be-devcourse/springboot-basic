package org.prgrms.deukyun.voucherapp.voucher.entity;

import java.util.UUID;

/**
 * 바우처
 */
public interface Voucher {

    /**
     * @return 바우처의 아이디
     */
    UUID getVoucherId();

    /**
     * 바우처의 할인 로직 적용
     *
     * @param beforeDiscount
     * @return 할인된 가격
     */
    long discount(long beforeDiscount);
}
