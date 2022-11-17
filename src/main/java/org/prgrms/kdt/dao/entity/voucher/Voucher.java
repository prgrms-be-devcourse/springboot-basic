package org.prgrms.kdt.dao.entity.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    String getDiscountValue();
}
