package com.prgrms.spring.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getDiscount();
}
