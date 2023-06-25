package org.prgrms.vouchermission;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();
    long discountAmount(long beforeDiscount);
    LocalDate getCreatedDate();
    LocalDate getExpirationDate();

}
