package org.programmers.springbootbasic.console.handler;

import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.programmers.springbootbasic.console.ConsoleResponseCode;
import org.programmers.springbootbasic.console.ModelAndView;
import org.programmers.springbootbasic.console.command.Command;
import org.springframework.stereotype.Component;

@Component
public interface Handler {
    void initCommandList();

    boolean supports(Command command);

    ModelAndView handleRequest(ConsoleRequest request);

    default ModelAndView processStaticPage(ConsoleRequest request, ConsoleResponseCode responseCode) {
        return new ModelAndView(request.getModel(), request.getCommand().getViewName(), responseCode);
    }
}
