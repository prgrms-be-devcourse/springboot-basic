package org.prgms.voucher.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getId();

    long discount(long price);
}
