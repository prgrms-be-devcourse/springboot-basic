package kr.co.programmers.springbootbasic.io.enums;

import kr.co.programmers.springbootbasic.global.exception.NoValidCommandException;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum EntireServiceCommand {
    EXIT(1),
    VOUCHER_SERVICE(2),
    CUSTOMER_SERVICE(3),
    WALLET_SERVICE(4);

    private static final Logger logger = LoggerFactory.getLogger(EntireServiceCommand.class);
    private final int command;

    EntireServiceCommand(int command) {
        this.command = command;
    }

    public static EntireServiceCommand resolve(String input) {
        int command = ApplicationUtils.parseStringToInteger(input);
        return Arrays.stream(values())
                .filter(val -> val.getCommand() == command)
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
