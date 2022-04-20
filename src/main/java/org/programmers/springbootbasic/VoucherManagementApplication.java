package org.programmers.springbootbasic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.ConsoleRequest;
import org.programmers.springbootbasic.console.ConsoleResponseCode;
import org.programmers.springbootbasic.console.Dispatcher;
import org.programmers.springbootbasic.console.Model;
import org.programmers.springbootbasic.console.input.Input;
import org.programmers.springbootbasic.console.input.InputCommandReader;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.INPUT;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.STOP;
import static org.programmers.springbootbasic.console.command.InputCommand.HOME;
import static org.programmers.springbootbasic.console.command.RedirectCommand.WAIT_FOR_INPUT;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherManagementApplication implements ApplicationRunner {
    //TODO: ConsoleRequest 객체 일일히 생성하지 말자

    private final Dispatcher dispatcher;
    private final Model model;
    private final InputCommandReader inputCommandReader;
    private final Input input;

    @Override
    public void run(ApplicationArguments args) {
        ConsoleResponseCode code = dispatcher.service(new ConsoleRequest(model, HOME));
        while (code != STOP) {
            if (code == INPUT) {
                log.debug("Input \"{}\" attribute", model.getInputSignature());
                model.addAttributes(model.getInputSignature(), input.readLine());

                log.info("Redirect to {}", model.getRedirectLink());
                code = dispatcher.service(new ConsoleRequest(model, model.getRedirectLink()));
            }
            else {
                dispatcher.service(new ConsoleRequest(model, WAIT_FOR_INPUT));
                code = dispatcher.service(inputCommandReader.assembleRequestMessage(model));
            }

//            if (code != INPUT && !model.hasRedirectLink()) {
//                dispatcher.service(new ConsoleRequest(model, WAIT_FOR_INPUT));
//                code = dispatcher.service(inputCommandReader.assembleRequestMessage(model));
//            }
//
//            if (code == INPUT) {
//                log.debug("Input \"{}\" attribute", model.getInputSignature());
//                model.addAttributes(model.getInputSignature(), input.readLine());
//            }
//
//            if (model.hasRedirectLink()) {
//                log.info("Redirect to {}", model.getRedirectLink());
//                code = dispatcher.service(model.getRedirectLink(), model));
//            }
        }
    }
}
