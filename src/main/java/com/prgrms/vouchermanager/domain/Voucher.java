package com.prgrms.vouchermanager.domain;

import java.util.UUID;


public interface Voucher {
    long discount(long beforeDiscount);
    String toString();
    UUID getId();
}
