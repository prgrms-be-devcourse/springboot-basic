package org.prgms.vouchermanagement.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum VoucherType {
    FIXED_AMOUNT_VOUCHER_TYPE(1),
    PERCENT_DISCOUNT_VOUCHER_TYPE(2);

    private final int type;

    public static VoucherType getVoucherType(int type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getType() == type)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION));
    }
}
