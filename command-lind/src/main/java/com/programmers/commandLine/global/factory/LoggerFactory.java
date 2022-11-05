package com.programmers.commandLine.global.factory;

import com.programmers.commandLine.CommandLindApplication;
import org.slf4j.Logger;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;

public class LoggerFactory {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CommandLindApplication.class);

    public static Logger getLogger() {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        return logger;
    }
}
