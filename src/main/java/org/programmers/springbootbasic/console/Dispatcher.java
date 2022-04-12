package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.command.InputCommand.HELP;

@Slf4j
@Component
@RequiredArgsConstructor
public class Dispatcher {

    private static final Map<String, Command> commandList = new ConcurrentHashMap<>();
    private final Controller controller;
    private final Drawer drawer;

    @PostConstruct
    static void initCommandList() {
        for (var command : InputCommand.values()) {
            commandList.put(command.getName(), command);
        }
        for (var command : RedirectCommand.values()) {
            commandList.put(command.getName(), command);
        }
    }

    public ConsoleResponseCode service(String input, Model model) {
        log.info("processing input {} at dispatcher", input);

        var command = readCommand(input);
        ModelAndView modelAndView = controller.process(command, model);

        ConsoleResponseCode responseCode;
        try {
            responseCode = drawer.draw(modelAndView);
        } catch (IOException e) {
            //TODO ERROR page 처리 : IOException
            responseCode = ConsoleResponseCode.ERROR;
        }
        return responseCode;
    }

    Command readCommand(String input) {
        var command = commandList.get(input);
        return (command != null) ? command : HELP;
    }
}