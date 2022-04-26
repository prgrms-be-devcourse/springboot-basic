package org.prgrms.spring_week1.Voucher.model;

import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();
}
