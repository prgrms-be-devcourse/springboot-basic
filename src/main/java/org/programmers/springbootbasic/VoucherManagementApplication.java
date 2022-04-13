package org.programmers.springbootbasic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.ConsoleResponseCode;
import org.programmers.springbootbasic.console.Dispatcher;
import org.programmers.springbootbasic.console.Input;
import org.programmers.springbootbasic.console.Model;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.INPUT;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.STOP;
import static org.programmers.springbootbasic.console.command.RedirectCommand.WAIT_FOR_INPUT;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherManagementApplication implements ApplicationRunner {

    private final Dispatcher dispatcher;
    private final Model model;
    private final Input input;

    @Override
    public void run(ApplicationArguments args) {
        ConsoleResponseCode code = dispatcher.service("home", model);
        while (code != STOP) {
            if (code != INPUT && !model.hasRedirectLink()) {
                dispatcher.service(WAIT_FOR_INPUT.getName(), model);
                code = dispatcher.service(input.readLine(), model);
            }

            if (code == INPUT) {
                log.debug("Input \"{}\" attribute", model.getInputSignature());
                model.addAttributes(model.getInputSignature(), input.readLine());
            }

            if (model.hasRedirectLink()) {
                log.info("Redirect to {}", model.getRedirectLink());
                code = dispatcher.service(model.getRedirectLink(), model);
            }
        }
    }
}
