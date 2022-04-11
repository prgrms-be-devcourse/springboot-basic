package org.prgms.voucher;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {
    Long apply(Long beforeDiscount);

    UUID getVoucherId();
}
