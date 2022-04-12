package com.manager.voucher.domain;

public interface Voucher {
    boolean isExpired();
    void applyDiscountTo(Item item);
}
