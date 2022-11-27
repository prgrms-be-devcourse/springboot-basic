package prgms.vouchermanagementapp.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long amountBeforeDiscount);
}
