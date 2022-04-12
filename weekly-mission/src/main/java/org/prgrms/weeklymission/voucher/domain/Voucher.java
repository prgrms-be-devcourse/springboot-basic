package org.prgrms.weeklymission.voucher.domain;

import java.util.UUID;

import static org.prgrms.weeklymission.utils.ErrorMessage.AFTER_DISCOUNT_PRICE_ERR;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    static void checkDiscountPrice(long beforeDiscount, long afterDiscount) {
        if(afterDiscount < 0 || afterDiscount > beforeDiscount) { //할인된 가격이 0보다 작거나 본래 가격보다 크다면 예외 던짐
            throw new RuntimeException(AFTER_DISCOUNT_PRICE_ERR.getMessage());
        }
    }
}