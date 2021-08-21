package com.example.kdtspringmission.voucher.domain;

import java.util.UUID;

public interface Voucher {
    long discountPrice(long price);

    UUID getId();
}
