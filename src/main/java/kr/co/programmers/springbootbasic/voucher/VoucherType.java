package kr.co.programmers.springbootbasic.voucher;

import kr.co.programmers.springbootbasic.exception.NoValidCommandException;
import kr.co.programmers.springbootbasic.util.VoucherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT(1),
    PERCENT_AMOUNT(2);
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private final int command;

    VoucherType(int command) {
        this.command = command;
    }

    public int getCommand() {
        return command;
    }

    public static VoucherType resolve(int input) {
        return Arrays.stream(values())
                .filter(val -> val.getCommand() == input)
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("{}과(와) 일치하는 바우처가 없습니다.", input);

                    return new NoValidCommandException(VoucherValue.NO_VALID_VOUCHER);
                });
    }
}
