package org.programmers.springbootbasic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.ConsoleResponseCode;
import org.programmers.springbootbasic.console.Dispatcher;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.request.Input;
import org.programmers.springbootbasic.console.request.RequestCreator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.*;
import static org.programmers.springbootbasic.console.command.InputCommand.HOME;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherManagementApplication implements ApplicationRunner {

    private final Dispatcher dispatcher;
    private final Model model;
    private final RequestCreator requestSetter;
    private final Input input;

    @Override
    public void run(ApplicationArguments args) {
        ConsoleResponseCode code = dispatcher.respond(requestSetter.createInputRequestMessage(model, HOME.getViewName()));
        while (code != STOP) {
            if (code == INPUT_AND_REDIRECT) {
                log.debug("Input {} attribute", model.getInputSignature());
                model.addAttributes(model.getInputSignature(), input.readLine());

                log.info("Redirect to {}", model.getRedirectLink());
                code = dispatcher.respond(requestSetter.createRedirectRequestMessage(model));
            } else if (code == REDIRECT) {
                log.info("Redirect to {}", model.getRedirectLink());
                code = dispatcher.respond(requestSetter.createRedirectRequestMessage(model));
            } else {
                code = dispatcher.respond(requestSetter.createInputRequestMessage(model, input.readLine()));
            }
        }
    }
}
