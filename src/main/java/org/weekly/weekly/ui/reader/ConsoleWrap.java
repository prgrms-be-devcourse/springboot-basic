package org.weekly.weekly.ui.reader;

import org.springframework.stereotype.Component;

import java.io.Console;

@Component
public class ConsoleWrap implements CommandReader{
    private final Console consoleWrap;
    public ConsoleWrap() {
        this.consoleWrap = System.console();
    }
    @Override
    public String readLine()  {
        return consoleWrap.readLine();
    }
}
