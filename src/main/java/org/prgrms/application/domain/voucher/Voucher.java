package org.prgrms.application.domain.voucher;

import java.util.UUID;

public interface Voucher {

    Long getVoucherId();

    double discount(double beforeDiscount);

    Voucher copy();
}
