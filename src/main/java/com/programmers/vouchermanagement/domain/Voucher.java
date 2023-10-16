package com.programmers.vouchermanagement.domain;

import java.util.UUID;

public interface Voucher {

    UUID getId();

    float discount(float beforeDiscount);
}
