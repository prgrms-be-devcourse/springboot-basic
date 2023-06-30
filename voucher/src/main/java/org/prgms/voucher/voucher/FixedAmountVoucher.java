package org.prgms.voucher.voucher;

import java.time.LocalDate;
import java.util.UUID;

public class FixedAmountVoucher implements AmountVoucher {
    private final UUID id;
    private final int amount;
    private final LocalDate publishDate;
    private final LocalDate expirationDate;
    private final VoucherOptionType voucherOptionType;

    public FixedAmountVoucher(UUID id, int amount) {
        this.id = id;
        this.amount = amount;
        this.publishDate = LocalDate.now();
        this.expirationDate = publishDate.plusMonths(6);
        this.voucherOptionType = VoucherOptionType.FIXED_AMOUNT;
    }

    @Override
    public int discount(int amountBeforeDiscount) {
        return Math.max((amountBeforeDiscount - amount), 0);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getOptionTypeName() {
        return voucherOptionType.getTypeName();
    }

    @Override
    public LocalDate getPublishDate() {
        return publishDate;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
