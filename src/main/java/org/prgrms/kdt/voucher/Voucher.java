package org.prgrms.kdt.voucher;

import java.util.Optional;

public interface Voucher {

    int discount(int beforeDiscount);

    String getVoucherId();

    int getAmount();

    String getVoucherType();

    Optional<String> getOwnerId();

    default boolean isOwned(){
        return getOwnerId().isPresent();
    }
}
