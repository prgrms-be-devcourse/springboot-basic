package org.weekly.weekly.ui.reader;

import java.io.Console;
import java.io.IOException;

public class ConsoleWrap implements CommandReader{
    private final Console consoleWrap;
    public ConsoleWrap() {
        this.consoleWrap = System.console();
    }
    @Override
    public String readLine() throws IOException {
        return null;
    }
}
