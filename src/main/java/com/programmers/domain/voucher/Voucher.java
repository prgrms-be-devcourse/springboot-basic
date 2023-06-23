package com.programmers.domain.voucher;

import java.time.LocalDate;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    boolean available();

}
