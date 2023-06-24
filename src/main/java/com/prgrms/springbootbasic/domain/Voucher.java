package com.prgrms.springbootbasic.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getDiscount();
}
