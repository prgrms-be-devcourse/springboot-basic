package org.prgms.vouchermanagement.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.prgms.vouchermanagement.voucher.exception.VoucherException;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum VoucherType {
    FIXED_AMOUNT_VOUCHER_TYPE(1),
    PERCENT_DISCOUNT_VOUCHER_TYPE(2);

    private final int type;

    private static final Map<Integer, VoucherType> VOUCHER_TYPE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(VoucherType::getType, Function.identity())));

    public static VoucherType getVoucherType(int type) {
        if (VOUCHER_TYPE_MAP.containsKey(type)) {
            return VOUCHER_TYPE_MAP.get(type);
        }
        throw new VoucherException(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);
    }
}
