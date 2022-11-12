package org.prgrms.kdt.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    void validate(long discountDegree);

}
