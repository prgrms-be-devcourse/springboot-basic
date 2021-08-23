package com.org.prgrms.mission1;



import java.util.UUID;


public interface Voucher {



    long discount(long beforeDiscount);

    UUID getVoucherID();
}
