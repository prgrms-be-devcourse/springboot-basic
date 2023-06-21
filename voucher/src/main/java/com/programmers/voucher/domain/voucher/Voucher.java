package com.programmers.voucher.domain.voucher;

public interface Voucher {
    String getVoucherId();

    long discount(long originalPrice);

}
