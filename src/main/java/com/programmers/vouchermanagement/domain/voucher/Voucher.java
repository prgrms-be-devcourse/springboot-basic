package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getId();

    float discount(float beforeDiscount);

    String joinInfo(String separator);
}
