package com.example.kdtspringmission.voucher.domain;

import com.example.kdtspringmission.customer.domain.Customer;
import java.util.UUID;

public interface Voucher {
    long discount(long price);

    UUID getId();

    void setOwnerId(UUID customer);

    String getTypeName();

    long getDiscountAmount();

    UUID getOwnerId();
}
