package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.voucher.exception.NoValidCommandException;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum MenuCommand {
    EXIT(1),
    CREATE(2),
    LIST(3),
    BLACK_LIST(4);

    private static final Logger logger = LoggerFactory.getLogger(MenuCommand.class);
    private final int command;

    MenuCommand(int command) {
        this.command = command;
    }

    public static MenuCommand resolve(String input) {
        int command = ApplicationUtils.parseStringToInteger(input);
        return Arrays.stream(values())
                .filter(val -> val.getCommand() == command)
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("{}와 일치하는 메뉴가 없습니다.", input);

                    return new NoValidCommandException("올바르지 않은 메뉴 선택입니다.\n\n");
                });
    }

    public int getCommand() {
        return command;
    }
}
