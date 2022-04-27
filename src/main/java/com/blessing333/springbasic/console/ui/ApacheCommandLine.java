package com.blessing333.springbasic.console.ui;

import org.apache.commons.cli.HelpFormatter;

public abstract class ApacheCommandLine {
    private final CommandOptions supportedCommandOptions = initSupportedCommandOption();
    private final HelpFormatter helpFormatter = new HelpFormatter();

    protected abstract CommandOptions initSupportedCommandOption();

    protected void printHelpText(String syntax) {
        helpFormatter.printHelp(syntax, supportedCommandOptions.getOptions());
    }
}
