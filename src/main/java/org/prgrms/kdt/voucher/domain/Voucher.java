package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public interface Voucher {
    void validate(long value);

    UUID voucherId();

    long discount(long beforeDiscount);

    String toString();

    boolean equals(Object o);

    int hashCode();

    long value();
}
