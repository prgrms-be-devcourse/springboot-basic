package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    String getVoucherType();
    Long discount(Long beforeDiscount);
}
