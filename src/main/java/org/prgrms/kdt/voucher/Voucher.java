package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    long discount(Long beforeDiscount);
    UUID getID();
    String toString();
}
