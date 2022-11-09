package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {

    double discount(double beforeDiscount);

    UUID getVoucherId();

    double getAmount();
}
