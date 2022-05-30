package me.programmers.springboot.basic.springbootbasic.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitCommand implements CommandStrategy{
    private static final Logger logger = LoggerFactory.getLogger(ExitCommand.class);

    @Override
    public void operateCommand() {
        logger.info("CommandLineApplication Terminate");
    }
}
