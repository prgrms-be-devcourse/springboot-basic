package prgms.vouchermanagementapp.service;

import prgms.vouchermanagementapp.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.model.PercentDiscountVoucher;
import prgms.vouchermanagementapp.model.Voucher;
import prgms.vouchermanagementapp.model.value.Amount;
import prgms.vouchermanagementapp.model.value.Ratio;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class VoucherFactory {

    private static final String FIXED_AMOUNT_VOUCHER = "FixedAmountVoucher";
    private static final String PERCENT_DISCOUNT_VOUCHER = "PercentDiscountVoucher";

    private VoucherFactory() {
    }

    public static Voucher createVoucher(String voucherType, long discountLevel) {
        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> createVoucher(new Amount(discountLevel));
            case PERCENT_DISCOUNT_VOUCHER -> createVoucher(new Ratio(discountLevel));
            default -> throw new IllegalArgumentException();
        };
    }

    public static Voucher createVoucher(Amount fixedDiscountAmount) {
        UUID voucherId = UUID.randomUUID();
        LocalDateTime createdDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return new FixedAmountVoucher(voucherId, fixedDiscountAmount, createdDateTime);
    }

    public static Voucher createVoucher(Ratio fixedDiscountRatio) {
        UUID voucherId = UUID.randomUUID();
        LocalDateTime createdDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return new PercentDiscountVoucher(voucherId, fixedDiscountRatio, createdDateTime);
    }
}
