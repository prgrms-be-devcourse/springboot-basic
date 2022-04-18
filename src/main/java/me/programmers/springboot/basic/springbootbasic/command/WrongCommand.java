package me.programmers.springboot.basic.springbootbasic.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrongCommand implements CommandStrategy {
    private static final Logger logger = LoggerFactory.getLogger(WrongCommand.class);

    @Override
    public void operateCommand() {
        logger.error("잘못된 명령어 입력 ");
    }
}
