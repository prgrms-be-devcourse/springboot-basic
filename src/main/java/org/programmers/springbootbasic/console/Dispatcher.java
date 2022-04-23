package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.model.ModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleMapper;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.REDIRECT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class Dispatcher {

    private final ConsoleMapper mappingData;
    private final Drawer drawer;

    public ConsoleResponseCode respond(ConsoleRequest request) {
        var model = request.getModel();
        try {
            var command = request.getCommand();
            var handler = mappingData.getHandler(command);

            ModelAndView modelAndView = handler.handleRequest(request);
            var code = drawer.draw(modelAndView);
            request = null; //TODO: PR 포인트2
            return code;
        } catch (Exception e) {
            log.error("Exception stack trace: ", e);
            model.addAttributes("errorData", e);
            model.setRedirectLink(ERROR);
            return REDIRECT;
        }
    }
}