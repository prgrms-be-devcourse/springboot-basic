package org.prgrms.application.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    double discount(double beforeDiscount);
}
