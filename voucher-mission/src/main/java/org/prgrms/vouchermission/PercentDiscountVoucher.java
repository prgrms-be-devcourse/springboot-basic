package org.prgrms.vouchermission;

import java.time.LocalDate;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;
    private static final String TYPE = "PERCENT";

    public PercentDiscountVoucher(long percent, LocalDate createdDate, LocalDate expirationDate) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discountAmount(long beforeDiscount) {
        return beforeDiscount * (100 - percent);
    }

}
