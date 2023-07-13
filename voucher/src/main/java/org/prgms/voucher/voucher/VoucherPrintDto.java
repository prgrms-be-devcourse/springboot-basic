package org.prgms.voucher.voucher;

import org.prgms.voucher.util.MessageUtils;

import java.time.LocalDate;

import static org.prgms.voucher.voucher.VoucherPrintType.*;

public class VoucherPrintDto {
    private final String creationTypeName;
    private final int discountAmount;
    private final LocalDate publishDate;
    private final LocalDate expirationDate;

    public VoucherPrintDto(AmountVoucher amountVoucher) {
        this.creationTypeName = amountVoucher.getCreationTypeName();
        this.discountAmount = amountVoucher.getDiscountAmount();
        this.publishDate = amountVoucher.getPublishDate();
        this.expirationDate = amountVoucher.getExpirationDate();
    }

    public String generateVoucherPrint() {
        return formatVoucherOutput(VOUCHER_TYPE, creationTypeName)
                + formatVoucherOutput(DISCOUNT_AMOUNT, String.valueOf(discountAmount))
                + formatVoucherOutput(PUBLISH_DATE, String.valueOf(publishDate))
                + formatVoucherOutput(EXPIRATION_DATE, String.valueOf(expirationDate));
    }

    private String formatVoucherOutput(VoucherPrintType voucherPrintType, String value) {
        return MessageUtils.getMessage(voucherPrintType.getType()) + ":" + value + "\n";
    }
}
