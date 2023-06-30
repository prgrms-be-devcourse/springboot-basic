package org.prgms.voucher.voucher;

import java.time.LocalDate;

public class VoucherPrintDto {
    String optionType;
    int afterDiscountValue;
    LocalDate publishDate;
    LocalDate expirationDate;

    public VoucherPrintDto(String optionType, int afterDiscountValue, LocalDate publishDate, LocalDate expirationDate) {
        this.optionType = optionType;
        this.afterDiscountValue = afterDiscountValue;
        this.publishDate = publishDate;
        this.expirationDate = expirationDate;
    }

    public String getOptionType() {
        return optionType;
    }

    public int getAfterDiscountValue() {
        return afterDiscountValue;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
