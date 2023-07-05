package org.prgms.voucher.voucher;

import java.time.LocalDate;
import java.util.UUID;

public abstract class AmountVoucher {
    private final UUID id;
    final int discountAmount;
    private final LocalDate publishDate;
    private final LocalDate expirationDate;
    private final AmountVoucherOptionType amountVoucherOptionType;

    protected AmountVoucher(int discountAmount, AmountVoucherOptionType amountVoucherOptionType) {
        validateDiscountAmount(discountAmount);

        this.id = UUID.randomUUID();
        this.discountAmount = discountAmount;
        this.publishDate = LocalDate.now();
        this.expirationDate = publishDate.plusMonths(6);
        this.amountVoucherOptionType = amountVoucherOptionType;
    }

    abstract int discount(int amountBeforeDiscount);

    public UUID getId() {
        return id;
    }

    public String getOptionTypeName() {
        return amountVoucherOptionType.getTypeName();
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount <= 0) {
            throw new IllegalArgumentException("할인 값은 0보다 커야합니다.");
        }
    }
}
