package com.org.prgrms.mission1;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherID();

    long discount(long beforeDiscount);
}
