package kr.co.programmers.springbootbasic.voucher.domain;

import kr.co.programmers.springbootbasic.global.exception.NoValidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT(1, 1),
    PERCENT_AMOUNT(2, 2);
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private final int typeId;
    private final int command;

    VoucherType(int typeId, int command) {
        this.typeId = typeId;
        this.command = command;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getCommand() {
        return command;
    }

    public static VoucherType resolveTypeId(int input) {
        return Arrays.stream(values())
                .filter(val -> val.getTypeId() == input)
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("바우처 종류 ID 중에 {}과(와) 일치하는 바우처가 없습니다.", input);

                    return new NoValidCommandException("올바르지 않은 바우처 종류 ID 입니다.\n\n");
                });
    }

    public static VoucherType resolveCommand(int input) {
        return Arrays.stream(values())
                .filter(val -> val.getCommand() == input)
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("{}과(와) 일치하는 바우처가 없습니다.", input);

                    return new NoValidCommandException("올바르지 않은 바우처 선택입니다.\n\n");
                });
    }
}
