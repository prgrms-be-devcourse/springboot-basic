package prgms.vouchermanagementapp.repository.util;

import prgms.vouchermanagementapp.domain.FixedAmountVoucher;
import prgms.vouchermanagementapp.domain.PercentDiscountVoucher;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class VoucherContentConverter {

    private static final int VOUCHER_ID_INDEX = 0;
    private static final int VOUCHER_TYPE_INDEX = 1;
    private static final int FIXED_DISCOUNT_LEVEL_INDEX = 2;

    private VoucherContentConverter() {
    }

    public static String toContent(Voucher voucher) {
        String voucherId = voucher.getVoucherId().toString();
        String voucherType = voucher.getClass().getSimpleName();
        long fixedDiscountLevel = voucher.getFixedDiscountLevel();

        return MessageFormat.format(
                "{0}, {1}, {2}",
                voucherId,
                voucherType,
                fixedDiscountLevel
        );
    }

    public static Voucher toVoucher(String voucherContentLiteral) {
        List<String> voucherContent = parseCsvContent(voucherContentLiteral);

        UUID voucherId = UUID.fromString(voucherContent.get(VOUCHER_ID_INDEX));
        String voucherType = voucherContent.get(VOUCHER_TYPE_INDEX);
        long fixedDiscountLevel = Long.parseLong(voucherContent.get(FIXED_DISCOUNT_LEVEL_INDEX));

        return createNewVoucher(voucherId, voucherType, fixedDiscountLevel);
    }

    private static List<String> parseCsvContent(String voucherContent) {
        return Arrays.stream(voucherContent.split(","))
                .map(String::trim)
                .toList();
    }

    private static Voucher createNewVoucher(UUID voucherId, String voucherType, long fixedDiscountLevel) {
        return switch (voucherType) {
            case "FixedAmountVoucher" -> new FixedAmountVoucher(voucherId, new Amount(fixedDiscountLevel));
            case "PercentDiscountVoucher" -> new PercentDiscountVoucher(voucherId, new Ratio(fixedDiscountLevel));
            default -> throw new IllegalArgumentException(
                    MessageFormat.format("Invalid voucher type: ''{0}''", voucherType)
            );
        };
    }
}
