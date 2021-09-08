package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherID();

    long discount(long beforDiscount);
}
