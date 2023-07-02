package com.programmers.console.util;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.dto.VoucherResponseDto;

import java.text.DecimalFormat;
import java.text.MessageFormat;

public class ResponseConverter {

    private static final String WRONG_DISCOUNT_TYPE_MESSAGE = "[ERROR] 올바르지 않은 Voucher Type 입니다.";
    private static final String PERCENT = "%";
    private static final String WON = "₩";

    public static String convertVoucherResponseToString(VoucherResponseDto responseDto) {
        return MessageFormat.format(ConsoleMessage.PRINT_VOUCHER_MESSAGE_FORM.getMessage(),
                responseDto.discount().getVoucherType(), responseDto.voucherId(),
                discountValueFormat(responseDto.discount()), responseDto.createdDate());
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
