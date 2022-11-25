package prgms.vouchermanagementapp.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getFixedDiscountLevel();

    long discount(long amountBeforeDiscount);
}
