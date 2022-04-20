package org.programmers.springbootbasic.console.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.ConsoleProperties;
import org.programmers.springbootbasic.console.ConsoleRequest;
import org.programmers.springbootbasic.console.ErrorData;
import org.programmers.springbootbasic.console.ModelAndView;
import org.programmers.springbootbasic.console.command.Command;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.RedirectCommand.ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorHandler implements Handler {

    private static final Map<String, Command> commandList = new ConcurrentHashMap<>();
    private final ConsoleProperties consoleProperties;

    @PostConstruct
    @Override
    public void initCommandList() {
        commandList.put(ERROR.getViewName(), ERROR);
    }

    @Override
    public boolean supports(Command command) {
        for (var supportingCommand : commandList.values()) {
            if (command == supportingCommand) {
                log.trace("Controller: {} supports command: {}.", this, command);
                return true;
            }
        }
        return false;
    }

    @Override
    public ModelAndView handleRequest(ConsoleRequest request) {
        log.info("processing command {} at Controller", request.getCommand());
        var model = request.getModel();
        if (consoleProperties.isDetailErrorMessage()) {
            var errorData = (Exception) model.getAttributes("errorData");
            model.addAttributes("errorMessage", errorData.getMessage());
            model.addAttributes("errorName", errorData.getClass());
        } else {
            var errorData = (ErrorData) model.getAttributes("errorData");
            model.addAttributes("errorMessage", errorData.message());
            model.addAttributes("errorName", errorData.name());
        }

        return new ModelAndView(model, "error/" + request.getCommand().getViewName(), PROCEED);
    }
}