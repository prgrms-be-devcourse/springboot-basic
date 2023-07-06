package kr.co.programmers.springbootbasic.io.enums;

import kr.co.programmers.springbootbasic.global.exception.NoValidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum CustomerServiceCommand {
    CREATE_CUSTOMER(1),
    FIND_CUSTOMER(2),
    UPDATE_CUSTOMER(3),
    DELETE_CUSTOMER(4);

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceCommand.class);
    private final int command;

    CustomerServiceCommand(int command) {
        this.command = command;
    }

    public static CustomerServiceCommand resolve(int input) {
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
