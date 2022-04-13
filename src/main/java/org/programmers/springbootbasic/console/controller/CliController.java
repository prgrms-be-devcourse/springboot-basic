package org.programmers.springbootbasic.console.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.Model;
import org.programmers.springbootbasic.console.ModelAndView;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.STOP;
import static org.programmers.springbootbasic.console.command.InputCommand.*;
import static org.programmers.springbootbasic.console.command.RedirectCommand.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CliController implements Controller {

    private static final Map<String, Command> commandList = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void initCommandList() {
        commandList.put(HELP.getName(), HELP);
        commandList.put(HOME.getName(), HOME);
        commandList.put(EXIT.getName(), EXIT);
        commandList.put(WAIT_FOR_INPUT.getName(), WAIT_FOR_INPUT);
    }

    @Override
    public boolean supports(Command command) {
        for (var supportingCommand : commandList.values()) {
            if (command == supportingCommand) {
                log.debug("Controller: {} supports command: {}.", this, command);
                return true;
            }
        }
        return false;
    }

    @Override
    public ModelAndView process(Command command, Model model) {
        log.info("processing command {} at Controller", command);
        return (command instanceof InputCommand inputCommand) ?
                processInputCommand(inputCommand, model) :
                processRedirectCommand((RedirectCommand) command, model);
    }

    private ModelAndView processInputCommand(InputCommand command, Model model) {
        switch (command) {
            case HOME:
                return home(HOME, model);
            case EXIT:
                return exit(EXIT, model);
            default:
                return help(HELP, model);
        }
    }

    private ModelAndView processRedirectCommand(RedirectCommand command, Model model) {
        switch (command) {
            case WAIT_FOR_INPUT:
                return waitForInput(WAIT_FOR_INPUT, model);
            default:
                return help(HELP, model);
        }
    }

    private ModelAndView home(InputCommand command, Model model) {
        return new ModelAndView(model, command.getName(), PROCEED);
    }

    private ModelAndView exit(InputCommand command, Model model) {
        return new ModelAndView(model, command.getName(), STOP);
    }

    private ModelAndView waitForInput(Command command, Model model) {
        return new ModelAndView(model, command.getName(), PROCEED);
    }

    private ModelAndView help(InputCommand command, Model model) {
        var commands = InputCommand.values();
        List<String> allCommandsInformation = new ArrayList<>();

        for (InputCommand eachCommand : commands) {
            allCommandsInformation.add(eachCommand.getCommandInformation());
        }

        model.addAttributes("allCommandsInformation", allCommandsInformation);

        return new ModelAndView(model, command.getName(), PROCEED);
    }
}
