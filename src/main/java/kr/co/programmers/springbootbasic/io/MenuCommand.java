package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.exception.NoValidCommandException;
import kr.co.programmers.springbootbasic.util.VoucherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum MenuCommand {
    EXIT(1),
    CREATE(2),
    LIST(3);

    private static final Logger logger = LoggerFactory.getLogger(MenuCommand.class);
    private final int command;

    MenuCommand(int command) {
        this.command = command;
    }

    public static MenuCommand resolve(String input) {
        int command = VoucherUtils.parseStringToInteger(input);
        return Arrays.stream(values())
                .filter(val -> val.getCommand() == command)
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("{}와 일치하는 메뉴가 없습니다.", input);

                    return new NoValidCommandException(ConsoleMessage.NO_VALID_MENU);
                });
    }

    public int getCommand() {
        return command;
    }
}
