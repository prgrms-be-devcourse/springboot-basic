package org.prgrms.kdt.models;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount();
}
