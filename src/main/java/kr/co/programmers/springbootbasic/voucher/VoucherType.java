package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.exception.NoValidCommandException;
import kr.co.programmers.springbootbasic.util.VoucherUtils;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER_COMMAND(1),
    PERCENT_AMOUNT_VOUCHER_COMMAND(2);
    private final int command;

    VoucherType(int command) {
        this.command = command;
    }

    public int getCommand() {
        return command;
    }

    public static VoucherType resolve(String input) {
        int unresolvedCommand = VoucherUtils.parseStringToInteger(input);
        return Arrays.stream(values())
                .filter(val -> val.getCommand() == unresolvedCommand)
                .findFirst()
                .orElseThrow(() -> new NoValidCommandException("올바르지 않은 바우처 선택입니다."));
    }
}
