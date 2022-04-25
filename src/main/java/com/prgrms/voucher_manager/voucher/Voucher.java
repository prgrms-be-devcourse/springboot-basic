package com.prgrms.voucher_manager.voucher;

import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();

    void validateValue(Long value);

    Long getValue();

    String getType();

    void changeValue(Long value);


}
