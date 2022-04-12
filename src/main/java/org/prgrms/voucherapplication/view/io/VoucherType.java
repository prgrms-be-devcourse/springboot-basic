package org.prgrms.voucherapplication.view.io;

import org.prgrms.voucherapplication.exception.InvalidVoucherTypeException;

import java.util.Arrays;
import java.util.Optional;

/**
 * 바우처 옵션을 정의한 enum class
 */
public enum VoucherType {
    FixedAmount("1"),
    PercentDiscount("2");

    private final String input;

    VoucherType(String input) {
        this.input = input;
    }

    /**
     * input이 "1"이면 FixedAmount 바우처 타입
     * input이 "2"면 PercentDiscount 바우처 타입
     * input이 그 이외의 문자이면 null
     *
     * @param input: 사용자의 입력
     * @return 선택된 바우처 타입
     */
    public static VoucherType getVoucherType(String input) throws InvalidVoucherTypeException {
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.input.equals(input))
                .findAny()
                .orElseThrow(InvalidVoucherTypeException::new);
    }
}
