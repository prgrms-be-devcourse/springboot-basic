package prgms.vouchermanagementapp.model.dto;

import java.text.MessageFormat;

public class VoucherCreationDto {

    private String voucherType;
    private long discountLevel;

    public VoucherCreationDto() {
    }

    public VoucherCreationDto(String voucherType, long discountLevel) {
        this.voucherType = switch (voucherType) {
            case "fixed" -> "FixedAmountVoucher";
            case "percent" -> "PercentDiscountVoucher";
            default -> throw new IllegalArgumentException(
                    MessageFormat.format(
                            "[ERROR] Voucher Type should be 'fixed' or 'percent'. You entered ''{0}''.", voucherType
                    )
            );
        };
        this.discountLevel = discountLevel;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getDiscountLevel() {
        return discountLevel;
    }
}

