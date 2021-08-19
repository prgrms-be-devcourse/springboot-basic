package org.prgrms.kdt.devcourse.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
}
