package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getId();

    int discount(int originalPrice);
}
