package prgms.vouchermanagementapp.domain;

import prgms.vouchermanagementapp.domain.value.Amount;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final Amount fixedDiscountAmount;
    private final LocalDateTime createdDateTime;

    public FixedAmountVoucher(UUID voucherId, Amount fixedDiscountAmount) {
        this.voucherId = voucherId;
        this.fixedDiscountAmount = fixedDiscountAmount;
        this.createdDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long amountBeforeDiscount) {
        long discountAmount = this.fixedDiscountAmount.getFixedDiscountLevel();
        if (amountBeforeDiscount < discountAmount) {
            throw new IllegalStateException(
                    MessageFormat.format("amount({0}) should be greater than discount amount({1}).",
                            amountBeforeDiscount,
                            discountAmount
                    )
            );
        }
        return amountBeforeDiscount - discountAmount;
    }

    @Override
    public long getFixedDiscountLevel() {
        return this.fixedDiscountAmount.getFixedDiscountLevel();
    }

    @Override
    public LocalDateTime getCreatedDateTime() {
        return this.createdDateTime;
    }
}
