package org.prgms.vouchermanagement.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long returnDiscount ();
}
