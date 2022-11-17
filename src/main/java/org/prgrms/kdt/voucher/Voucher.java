package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {

    int discount(int beforeDiscount);

    UUID getVoucherId();

    int getAmount();
}
