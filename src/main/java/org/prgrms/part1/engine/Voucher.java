package org.prgrms.part1.engine;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    Long getValue();
}
