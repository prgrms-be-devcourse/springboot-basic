package org.programmers.springbootbasic.console.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.ConsoleProperties;
import org.programmers.springbootbasic.console.SimpleErrorMessageMapper;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.model.ModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
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
    private final SimpleErrorMessageMapper simpleErrorMessageMapper;

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
        var errorData = (Exception) model.getAttributes("errorData");

        if (consoleProperties.isDetailErrorMessage()) {
            model.addAttributes("errorMessage", errorData.toString());
            model.addAttributes("errorName", errorData.getClass());
        } else {
            SimpleErrorMessageMapper.ErrorData simpleErrorData = simpleErrorMessageMapper.toErrorData(errorData);
            model.addAttributes("errorMessage", simpleErrorData.message());
            model.addAttributes("errorName", simpleErrorData.name());
        }

        return new ModelAndView(model, request.getCommand().getViewName(), PROCEED);
    }
}