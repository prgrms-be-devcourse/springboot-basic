package org.prgrms.vouchermanager.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    MenuType getType();
    int getAmount();
}
