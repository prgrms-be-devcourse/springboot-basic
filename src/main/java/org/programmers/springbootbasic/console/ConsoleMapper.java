package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.programmers.springbootbasic.console.handler.CliHandler;
import org.programmers.springbootbasic.console.handler.ErrorHandler;
import org.programmers.springbootbasic.console.handler.Handler;
import org.programmers.springbootbasic.console.handler.VoucherHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsoleMapper {

    public static final Map<String, Command> COMMANDS = new ConcurrentHashMap<>();
    public static final List<Handler> CONTROLLERS = new ArrayList<>();

    private final CliHandler cliController;
    private final VoucherHandler voucherController;
    private final ErrorHandler errorController;

    public Handler getController(Command command) {
        for (var controller : CONTROLLERS) {
            if (controller.supports(command)) {
                return controller;
            }
        }
        log.error("No controller handling command {} exist.", command);
        throw new IllegalStateException(
                "해당하는 컨트롤러가 없습니다. 어떤 명령어도 해당되지 않을 경우 기본적으로 HELP 명령어로 전환되기에," +
                        "기본 컨트롤러 설정을 다시 살펴보십시오.");
    }

    @PostConstruct
    void init() {
        initCommandList();
        initControllerList();
    }

    private void initCommandList() {
        for (var command : InputCommand.values()) {
            COMMANDS.put(command.getViewName(), command);
        }
        for (var command : RedirectCommand.values()) {
            COMMANDS.put(command.getViewName(), command);
        }
    }

    private void initControllerList() {
        CONTROLLERS.add(cliController);
        CONTROLLERS.add(voucherController);
        CONTROLLERS.add(errorController);
    }
}
