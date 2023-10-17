package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public interface Voucher {
    void setId(UUID id);
    UUID getId();
    long getAmount();
    VoucherType getType();
    String toString();
}
