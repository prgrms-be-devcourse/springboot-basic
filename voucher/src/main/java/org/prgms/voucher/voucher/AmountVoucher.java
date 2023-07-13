package org.prgms.voucher.voucher;

import java.time.LocalDate;
import java.util.UUID;

public abstract class AmountVoucher {
    private final UUID id;
    private final LocalDate publishDate;
    private final LocalDate expirationDate;
    private final int discountAmount;
    private final AmountVoucherCreationType amountVoucherCreationType;

    protected AmountVoucher(int discountAmount, AmountVoucherCreationType amountVoucherCreationType) {
        this.discountAmount = validateDiscountAmount(discountAmount);
        this.id = UUID.randomUUID();
        this.publishDate = LocalDate.now();
        this.expirationDate = publishDate.plusMonths(6);
        this.amountVoucherCreationType = amountVoucherCreationType;
    }

    abstract int validateDiscountAmount(int discountAmount);

    abstract int discount(int originalPrice);

    public UUID getId() {
        return id;
    }

    public String getCreationTypeName() {
        return amountVoucherCreationType.getTypeName();
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }
}
