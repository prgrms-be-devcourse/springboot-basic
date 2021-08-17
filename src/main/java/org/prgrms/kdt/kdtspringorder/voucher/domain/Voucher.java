package org.prgrms.kdt.kdtspringorder.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

}
