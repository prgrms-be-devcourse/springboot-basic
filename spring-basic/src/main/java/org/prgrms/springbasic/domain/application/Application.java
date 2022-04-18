package org.prgrms.springbasic.domain.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.service.console.CommandType;
import org.prgrms.springbasic.utils.io.console.Console;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.prgrms.springbasic.utils.exception.NonExistentCommand;
import org.springframework.stereotype.Component;

import static org.prgrms.springbasic.utils.enumm.message.ConsoleMessage.INIT_MESSAGE;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.COMMAND_ERROR;

@Component
@Slf4j
@RequiredArgsConstructor
public class Application implements Runnable {

    private final ConsoleService consoleService;
    private final Console console;

    @Override
    public void run() {
        log.info("program start");

        while (true) {
            console.printToConsole(INIT_MESSAGE.getMessage());

            try {
                var commandType = validateCommand(console.takeInput());

                commandType.action(consoleService);
            } catch (Exception e) {
                console.printToConsole(e);
            }
        }
    }

    private CommandType validateCommand(String command){

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Non existent command: {}", command);
            throw new NonExistentCommand(COMMAND_ERROR.getMessage());
        }

        return commandType;
    }
}