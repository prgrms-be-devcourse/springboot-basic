package org.prgms.voucher.voucher;

import java.time.LocalDate;
import java.util.UUID;

public interface AmountVoucher {
    int discount(int amountBeforeDiscount);
    UUID getId();
    String getOptionTypeName();
    LocalDate getPublishDate();
    LocalDate getExpirationDate();
}
