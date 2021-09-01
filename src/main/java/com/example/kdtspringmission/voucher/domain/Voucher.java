package com.example.kdtspringmission.voucher.domain;

import java.util.UUID;

public interface Voucher {
    long discount(long price);

    UUID getId();

    boolean support(VoucherType type);
}
