package org.prgms.vouchermanagement.voucher.domain.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long returnDiscount ();
}
