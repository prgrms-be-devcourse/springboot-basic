package org.prgrms.springbootbasic.entity;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {

    UUID getVoucherId();

    long discount(long beforeDiscount);
}
