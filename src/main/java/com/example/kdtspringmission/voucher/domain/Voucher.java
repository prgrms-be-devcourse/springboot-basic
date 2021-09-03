package com.example.kdtspringmission.voucher.domain;

import com.example.kdtspringmission.customer.domain.Customer;
import java.util.UUID;

public interface Voucher {
    long discount(long price);

    UUID getId();

    boolean support(VoucherType type);

    void setOwner(Customer customer);

    Customer getOwner();
}
