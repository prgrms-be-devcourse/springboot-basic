package kr.co.programmers.springbootbasic.io;

import kr.co.programmers.springbootbasic.exception.NoValidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum MenuCommand {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private static final Logger logger = LoggerFactory.getLogger(MenuCommand.class);
    private final String command;

    MenuCommand(String command) {
        this.command = command;
    }

    public static MenuCommand resolve(String input) {
        return Arrays.stream(values())
                .filter(val -> val.getCommand().equals(input))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warn("{}와 일치하는 메뉴가 없습니다.", input);

                    return new NoValidCommandException(ConsoleMessage.NO_VALID_MENU);
                });
    }

    public String getCommand() {
        return command;
    }
}
