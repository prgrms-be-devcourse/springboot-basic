package prgms.vouchermanagementapp.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    long discount(long amountBeforeDiscount);

    UUID getVoucherId();

    long getDiscountLevel();

    default String getVoucherType() {
        return this.getClass().getSimpleName();
    }

    LocalDateTime getCreatedDateTime();
}
