package org.prgrms.kdt.Voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();
    TypeStatus getType();
    long getVoucherdiscount();


    long discount(long beforeDiscount);

}
