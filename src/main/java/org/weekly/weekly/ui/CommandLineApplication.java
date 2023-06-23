package org.weekly.weekly.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.reader.CommandReader;
import org.weekly.weekly.ui.writer.CommandWriter;

@Component
public class CommandLineApplication {
    private final CommandReader commandReader;
    private final CommandWriter commandWriter;

    @Autowired
    public CommandLineApplication(CommandReader commandReader, CommandWriter commandWriter) {
        this.commandReader = commandReader;
        this.commandWriter = commandWriter;
    }

    public void read() {

    }
}
