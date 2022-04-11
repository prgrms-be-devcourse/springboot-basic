package com.programmers.springbootbasic.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    Long discount(Long beforeDiscount);

}
