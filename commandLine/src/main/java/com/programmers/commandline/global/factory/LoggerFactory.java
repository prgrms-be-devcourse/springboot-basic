package com.programmers.commandline.global.factory;

import com.programmers.commandline.CommandLineApplication;
import org.slf4j.Logger;
import org.springframework.boot.ansi.AnsiOutput;

public class LoggerFactory {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CommandLineApplication.class);

    public static Logger getLogger() {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        return logger;
    }
}