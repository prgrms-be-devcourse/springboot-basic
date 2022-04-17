package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long discount(Long beforeDiscount);
    String getInfo();
}
