package org.prgrms.kdt.domain;

import lombok.Getter;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discountCoupon();

    long discount(long beforeDiscount);

}
