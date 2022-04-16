package org.prgms.voucherProgram.domain.voucher;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {
    long discount(long beforeDiscount);

    UUID getVoucherId();
}
