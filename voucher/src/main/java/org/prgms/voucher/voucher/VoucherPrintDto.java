package org.prgms.voucher.voucher;

import org.prgms.voucher.util.MessageUtils;

import java.time.LocalDate;

import static org.prgms.voucher.voucher.VoucherPrintType.*;

public class VoucherPrintDto {
    private final String voucherType;
    private final int afterDiscountValue;
    private final LocalDate publishDate;
    private final LocalDate expirationDate;

    public VoucherPrintDto(String voucherType, int afterDiscountValue, LocalDate publishDate, LocalDate expirationDate) {
        this.voucherType = voucherType;
        this.afterDiscountValue = afterDiscountValue;
        this.publishDate = publishDate;
        this.expirationDate = expirationDate;
    }

    public String getVoucherPrint() {
        return formatVoucherOutput(VOUCHER_TYPE, voucherType)
                + formatVoucherOutput(AFTER_DISCOUNT_VALUE, String.valueOf(afterDiscountValue))
                + formatVoucherOutput(PUBLISH_DATE, String.valueOf(publishDate))
                + formatVoucherOutput(EXPIRATION_DATE, String.valueOf(expirationDate));
    }

    private String formatVoucherOutput(VoucherPrintType voucherPrintType, String value) {
        return MessageUtils.getMessage(voucherPrintType.getType()) + ":" + value + "\n";
    }
}
