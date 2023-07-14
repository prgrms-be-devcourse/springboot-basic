package org.weekly.weekly.ui.reader;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.Console;

@Component
@ConditionalOnProperty(value="command.read", havingValue = "console")
public class ConsoleWrap implements CommandReader{
    private final Console console;

    public ConsoleWrap() {
        console = System.console();
    }

    @Override
    public String readLine()  {
        return console.readLine();
    }
}
