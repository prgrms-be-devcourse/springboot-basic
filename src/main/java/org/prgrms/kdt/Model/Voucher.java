package org.prgrms.kdt.Model;

import org.prgrms.kdt.TypeStatus;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();
    TypeStatus getType();
    long getVoucherdiscount();


    long discount(long beforeDiscount);

}
