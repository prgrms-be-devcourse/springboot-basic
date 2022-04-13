package org.programmers.springbootbasic.console.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.ConsoleProperties;
import org.programmers.springbootbasic.console.ErrorData;
import org.programmers.springbootbasic.console.Model;
import org.programmers.springbootbasic.console.ModelAndView;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.RedirectCommand.ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorController implements Controller {

    private static final Map<String, Command> commandList = new ConcurrentHashMap<>();
    private final ConsoleProperties consoleProperties;

    @PostConstruct
    @Override
    public void initCommandList() {
        commandList.put(ERROR.getName(), ERROR);
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
    public ModelAndView process(Command command, Model model) {
        log.info("processing command {} at Controller", command);
        return processRedirectCommand((RedirectCommand) command, model);
    }

    private ModelAndView processRedirectCommand(RedirectCommand command, Model model) {
        switch (command) {
            case ERROR:
                return error(ERROR, model);
        }
        log.error("No controller handling command {} exist.", command);
        throw new IllegalStateException(
                "컨트롤러가 해당 커맨드를 처리하지 못 합니다. 컨트롤러 매핑이 잘못되었는지 확인해주세요.");
    }

    private ModelAndView error(Command command, Model model) {
        if (consoleProperties.isDetailErrorMessage()) {
            var errorData = (Exception) model.getAttributes("errorData");
            model.addAttributes("errorMessage", errorData.getMessage());
            model.addAttributes("errorName", errorData.getClass());
        } else {
            var errorData = (ErrorData) model.getAttributes("errorData");
            model.addAttributes("errorMessage", errorData.message());
            model.addAttributes("errorName", errorData.name());
        }

        model.setNoRedirectLink();
        return new ModelAndView(model, "error/" + command.getName(), PROCEED);
    }
}