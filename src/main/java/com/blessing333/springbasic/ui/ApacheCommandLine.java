package com.blessing333.springbasic.ui;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.cli.HelpFormatter;

@Getter(AccessLevel.PROTECTED)
public abstract class ApacheCommandLine {
    private final CommandOptions supportedCommandOptions = initSupportedCommandOption();
    private final HelpFormatter helpFormatter = new HelpFormatter();

    protected abstract CommandOptions initSupportedCommandOption();

    protected void printHelpText(String syntax) {
        helpFormatter.printHelp(syntax, supportedCommandOptions.getOptions());
    }
}
