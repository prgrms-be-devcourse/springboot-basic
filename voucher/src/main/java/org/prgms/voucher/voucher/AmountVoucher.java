package org.prgms.voucher.voucher;

import java.time.LocalDate;
import java.util.UUID;

public abstract class AmountVoucher {
    private final UUID id;
    private final LocalDate publishDate;
    private final LocalDate expirationDate;
    private final AmountVoucherCreationType amountVoucherCreationType;

    protected AmountVoucher(AmountVoucherCreationType amountVoucherCreationType) {
        this.id = UUID.randomUUID();
        this.publishDate = LocalDate.now();
        this.expirationDate = publishDate.plusMonths(6);
        this.amountVoucherCreationType = amountVoucherCreationType;
    }

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
}
