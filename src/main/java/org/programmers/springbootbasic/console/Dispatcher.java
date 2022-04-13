package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.programmers.springbootbasic.console.controller.CliController;
import org.programmers.springbootbasic.console.controller.Controller;
import org.programmers.springbootbasic.console.controller.ErrorController;
import org.programmers.springbootbasic.console.controller.VoucherController;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.InputCommand.HELP;

@Slf4j
@Component
@RequiredArgsConstructor
public class Dispatcher {

    private static final Map<String, Command> commandList = new ConcurrentHashMap<>();
    private static final List<Controller> controllerList = new ArrayList<>();

    private Controller controller;
    private final CliController cliController;
    private final VoucherController voucherController;
    private final ErrorController errorController;

    private final Drawer drawer;
    private final ConsoleProperties consoleProperties;

    @PostConstruct
    void init() {
        initCommandList();
        initControllerList();
    }

    private void initCommandList() {
        for (var command : InputCommand.values()) {
            commandList.put(command.getName(), command);
        }
        for (var command : RedirectCommand.values()) {
            commandList.put(command.getName(), command);
        }
    }

    private void initControllerList() {
        controllerList.add(cliController);
        controllerList.add(voucherController);
        controllerList.add(errorController);
    }

    public ConsoleResponseCode service(String input, Model model) {
        input = input.toLowerCase();
        //TODO: 악의적으로 REDIRECT 링크로 접속할 시 차단하기
        log.info("processing input {} at dispatcher", input);

        var command = readCommand(input);
        try {
            this.controller = searchProperController(command);

            ModelAndView modelAndView = this.controller.process(command, model);
            return drawer.draw(modelAndView);

        } catch (Exception e) {
            handleException(e, model);
            model.setRedirectLink("error");
            return PROCEED;
        }
    }

    private void handleException(Exception e, Model model) {
        if (e instanceof IllegalStateException) {
            model.addAttributes("errorData",
                    (consoleProperties.isDetailErrorMessage()) ? e :
                            new ErrorData("프로그램 내부 연결 오류", ""));
        } else if (e instanceof IllegalArgumentException) {
            model.addAttributes("errorData",
                    (consoleProperties.isDetailErrorMessage()) ? e :
                            new ErrorData("잘못된 값을 입력",
                                    "적절한 값을 입력해주세요."));
        }
    }

    private Controller searchProperController(Command command) {
        for (var eachController : controllerList) {
            if (eachController.supports(command)) {
                return eachController;
            }
        }

        log.error("No controller handling command {} exist.", command);
        throw new IllegalStateException(
                "해당하는 컨트롤러가 없습니다. 어떤 명령어도 해당되지 않을 경우 기본적으로 HELP 명령어로 전환되기에," +
                        "기본 컨트롤러 설정을 다시 살펴보십시오.");
    }

    Command readCommand(String input) {
        var command = commandList.get(input);
        return (command != null) ? command : HELP;
    }
}