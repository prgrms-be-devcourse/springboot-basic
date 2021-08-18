package org.prgrms.kdtbespring.entity;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

}
