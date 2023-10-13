package com.example.vouchermanager.console;

import com.example.vouchermanager.message.ConsoleMessage;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler {
    public Command run() {
        System.out.println(ConsoleMessage.SELECT_FUNCTION);

        switch (Reader.sc.nextLine()) {
            case "exit" -> {
                return Command.EXIT;
            }
            case "create" -> {
                return Command.CREATE;
            }
            case "list" -> {
                return Command.LIST;
            }
        }

        return null;
    }
}
