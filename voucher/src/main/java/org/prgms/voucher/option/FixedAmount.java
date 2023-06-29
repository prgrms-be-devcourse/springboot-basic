package org.prgms.voucher.option;

import org.prgms.voucher.type.OptionType;
import org.prgms.voucher.voucher.Voucher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmount implements Voucher {
    private final UUID fixedAmountId;
    private final int fixedAmount;
    private final LocalDate publishDate;
    private final LocalDate expirationDate;
    private final LocalDateTime createdAt;

    public FixedAmount(UUID fixedAmountId, int fixedAmount) {
        this.fixedAmountId = fixedAmountId;
        this.fixedAmount = fixedAmount;
        this.publishDate = LocalDate.now();
        this.expirationDate = publishDate.plusMonths(6);
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public UUID getVoucherId() {
        return fixedAmountId;
    }

    @Override
    public String getOptionType() {
        return OptionType.FIXED_AMOUNT.getOptionType();
    }

    @Override
    public LocalDate getPublishDate() {
        return publishDate;
    }


    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public int discount(int amountBeforeDiscount) {
        return Math.max((amountBeforeDiscount - fixedAmount), 0);
    }
}
