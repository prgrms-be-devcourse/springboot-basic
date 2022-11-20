package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public interface Voucher {

    long getVoucherId();

    long getDiscountDegree();

    String getTypeName();

    void validate(long discountDegree);

    Voucher changeDiscountDegree(long discountDegree);

}
