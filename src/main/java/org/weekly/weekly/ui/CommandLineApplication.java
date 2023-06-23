package org.weekly.weekly.ui;

import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.writer.CommandWriter;

public class CommandLineApplication {
    private final CommandReader commandReader;
    private final CommandWriter commandWriter;

    public CommandLineApplication(CommandReader commandReader, CommandWriter commandWriter) {
        this.commandReader = commandReader;
        this.commandWriter = commandWriter;
    }

    public void read() {

    }
}
