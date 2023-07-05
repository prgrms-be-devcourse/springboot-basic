package com.programmers.console.util;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.DiscountType;
import com.programmers.voucher.dto.VoucherResponseDto;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherStringSerializer {

    private VoucherStringSerializer() {
    }

    private static final String WRONG_DISCOUNT_TYPE_MESSAGE = "[ERROR] 올바르지 않은 Voucher Type 입니다.";
    private static final String PERCENT = "%";
    private static final String WON = "₩";

    private static DiscountType discountType;
    private static UUID voucherId;
    private static Discount discount;
    private static LocalDateTime createdAt;

    public static String convertVoucherResponseToString(VoucherResponseDto responseDto) {
        discountType = responseDto.discount().getVoucherType();
        voucherId = responseDto.voucherId();
        discount = responseDto.discount();
        createdAt = responseDto.createdAt();
        return MessageFormat.format(ConsoleMessage.PRINT_VOUCHER_MESSAGE_FORM.getMessage(),
                discountType, voucherId, discountValueFormat(discount), createdAt);
    }

    private static String discountValueFormat(Discount discount) {
        switch (discount.getVoucherType()) {
            case FIXED -> {
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                return formatter.format(discount.getAmount()) + WON;
            }
            case PERCENT -> {
                return discount.getAmount() + PERCENT;
            }
        }
        throw new IllegalArgumentException(WRONG_DISCOUNT_TYPE_MESSAGE);
    }
}
