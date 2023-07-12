package org.devcourse.springbasic.domain.voucher.domain;

import org.devcourse.springbasic.global.util.DigitChecker;

import java.util.Arrays;


public enum VoucherMenuType {

    EXIT(1),
    CREATE(2),
    LIST(3);

    private final int inputNum;

    VoucherMenuType(int inputNum) {
        this.inputNum = inputNum;
    }

    public static VoucherMenuType findVoucherMenuByInput(String input) {

        if (DigitChecker.isDigit(input)) {
            return Arrays.stream(VoucherMenuType.values())
                    .filter(voucherMenuType -> (voucherMenuType.inputNum == Integer.parseInt(input)))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));
        } else {
            return VoucherMenuType.valueOf(input.toUpperCase());
        }
    }

    public boolean isExit() {
        return this == EXIT;
    }
}
