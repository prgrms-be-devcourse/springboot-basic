package org.weekly.weekly.ui.reader;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.Console;

@Component
@ConditionalOnProperty(value="command.read", havingValue = "console")
public class ConsoleWrap implements CommandReader{
    private final Console consoleWrap;

    public ConsoleWrap() {
        System.out.println("consol");
        this.consoleWrap = System.console();
    }

    @Override
    public String readLine()  {
        return consoleWrap.readLine();
    }
}
