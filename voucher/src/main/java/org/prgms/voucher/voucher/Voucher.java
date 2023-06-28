package org.prgms.voucher.voucher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    String getOptionType();

    LocalDate getPublishDate();

    LocalDate getExpirationDate();

    LocalDateTime getCreatedAt();

    int discount(int amountBeforeDiscount);
}
