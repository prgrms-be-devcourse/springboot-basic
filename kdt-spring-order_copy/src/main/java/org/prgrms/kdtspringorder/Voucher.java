package org.prgrms.kdtspringorder;

import java.util.UUID;
import java.util.Optional;
public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
}
