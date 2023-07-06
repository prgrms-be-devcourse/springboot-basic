package kr.co.programmers.springbootbasic.io.enums;

import kr.co.programmers.springbootbasic.global.exception.NoValidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum CustomerFindCommand {
    FIND_BY_CUSTOMER_ID(1),
    FIND_BY_VOUCHER_ID(2),
    FIND_ALL(3),
    FIND_ALL_BLACK_CUSTOMER(4);


    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceCommand.class);
    private final int command;

    CustomerFindCommand(int command) {
        this.command = command;
    }

    public static CustomerFindCommand resolve(int input) {
        return Arrays.stream(values())
                .filter(val -> val.getCommand() == input)
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("{}와 일치하는 서비스가 없습니다.", input);

                    return new NoValidCommandException("지원하지 않은 서비스입니다.\n\n");
                });
    }

    public int getCommand() {
        return command;
    }
}
