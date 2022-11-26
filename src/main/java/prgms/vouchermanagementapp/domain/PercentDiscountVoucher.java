package prgms.vouchermanagementapp.domain;

import prgms.vouchermanagementapp.domain.value.Ratio;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final Ratio fixedDiscountRatio;
    private final LocalDateTime createdDateTime;

    public PercentDiscountVoucher(UUID voucherId, Ratio fixedDiscountRatio) {
        this.voucherId = voucherId;
        this.fixedDiscountRatio = fixedDiscountRatio;
        this.createdDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long amountBeforeDiscount) {
        return amountBeforeDiscount - (amountBeforeDiscount * (fixedDiscountRatio.getFixedDiscountLevel() / 100));
    }

    @Override
    public long getFixedDiscountLevel() {
        return this.fixedDiscountRatio.getFixedDiscountLevel();
    }

    @Override
    public LocalDateTime getCreatedDateTime() {
        return this.createdDateTime;
    }
}
