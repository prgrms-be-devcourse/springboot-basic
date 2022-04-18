package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(Long beforeDiscount);
    String getInfo();
}
