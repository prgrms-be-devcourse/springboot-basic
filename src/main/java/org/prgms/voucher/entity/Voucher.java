package org.prgms.voucher.entity;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {
    long discount(long beforeDiscount);

    UUID getVoucherId();
}
