package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long getValue();
    Long discount(Long beforeDiscount);
    String getType();
}
