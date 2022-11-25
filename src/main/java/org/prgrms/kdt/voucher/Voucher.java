package org.prgrms.kdt.voucher;

import java.util.Optional;

public interface Voucher {

    Integer discount(int beforeDiscount);

    String getVoucherId();

    Integer getAmount();

    String getVoucherType();

    Optional<String> getOwnerId();
}
