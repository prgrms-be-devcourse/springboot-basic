package org.prgms.voucher.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getId();

    void setId(UUID uuid);

    long discount(long price);
}
