package com.org.prgrms.mission.model;



import java.util.UUID;


public interface Voucher {



    long discount(long beforeDiscount);

    UUID getVoucherID();
}
