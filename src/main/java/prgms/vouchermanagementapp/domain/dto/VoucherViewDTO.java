package prgms.vouchermanagementapp.domain.dto;

import prgms.vouchermanagementapp.domain.Voucher;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.UUID;

public class VoucherViewDTO {

    private final UUID voucherId;
    private final String voucherType;
    private final String discountLevel;
    private final String createdDateTime;

    public VoucherViewDTO(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherType = parseVoucherType(voucher);
        this.discountLevel = parseDiscountLevel(voucher);
        this.createdDateTime = parseCreatedDateTime(voucher);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getDiscountLevel() {
        return discountLevel;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    private String parseVoucherType(Voucher voucher) {
        return switch (voucher.getVoucherType()) {
            case "FixedAmountVoucher" -> "고정 금액 할인";
            case "PercentDiscountVoucher" -> "비율 할인";
            default -> throw new IllegalArgumentException(
                    MessageFormat.format("voucher type ''{0}'' is not supported.", voucherType)
            );
        };
    }

    private String parseDiscountLevel(Voucher voucher) {
        return switch (voucher.getVoucherType()) {
            case "FixedAmountVoucher" -> voucher.getDiscountLevel() + "원";
            case "PercentDiscountVoucher" -> voucher.getDiscountLevel() + "%";
            default -> throw new IllegalArgumentException(
                    MessageFormat.format("voucher type ''{0}'' is not supported.", voucherType)
            );
        };
    }

    private String parseCreatedDateTime(Voucher voucher) {
        return voucher.getCreatedDateTime()
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
