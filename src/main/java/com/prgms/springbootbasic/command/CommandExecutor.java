package com.prgms.springbootbasic.command;

import com.prgms.springbootbasic.util.Menu;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandExecutor {

    private final Map<String, Command> commandMap;

    public CommandExecutor(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public void executeCommand(Menu menu) {
        String beanName = menu.getCommandBeanName();
        Command command = commandMap.get(beanName);
        command.execute();
    }

}
