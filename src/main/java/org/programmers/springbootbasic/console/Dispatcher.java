package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
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
            var controller = mappingData.getController(command);

            ModelAndView modelAndView = controller.handleRequest(request);
            var code = drawer.draw(modelAndView);
            request = null;
            return code;
        } catch (Exception e) {
            model.setRedirectLink(ERROR);
            return PROCEED;
        }
    }
}