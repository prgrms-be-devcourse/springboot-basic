package org.programmers.springbootbasic.console.handler;

import org.programmers.springbootbasic.console.ConsoleResponseCode;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.model.ConsoleModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.springframework.stereotype.Component;

@Component
public interface Handler {
    void initCommandList();

    boolean supports(Command command);

    ConsoleModelAndView handleRequest(ConsoleRequest request);

    default ConsoleModelAndView processStaticPage(ConsoleRequest request, ConsoleResponseCode responseCode) {
        return new ConsoleModelAndView(request.getConsoleModel(), request.getCommand().getViewName(), responseCode);
    }
}
