package com.prgrms.voucher_manager.voucher;

import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherID();

    void validateValue(Long value);

    Long getValue();

    void changeValue(Long value);


}
