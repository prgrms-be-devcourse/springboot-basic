package org.prgrms.kdt.Model;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();


    long discount(long beforeDiscount);
    long getdiscount();
}
