package org.prgms.voucher;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {
    long apply(long beforeDiscount);

    UUID getVoucherId();
}
