package org.prgrms.application.domain;

import java.util.UUID;

public interface Voucher<T> {

    UUID getVoucherId();

    T discount(T beforeDiscount);
}
