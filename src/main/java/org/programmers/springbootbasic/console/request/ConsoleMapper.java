package org.programmers.springbootbasic.console.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.programmers.springbootbasic.console.handler.*;
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

    static final Map<String, Command> COMMANDS = new ConcurrentHashMap<>();
    static final List<Handler> HANDLERS = new ArrayList<>();

    private final CliHandler cliHandler;
    private final VoucherHandler voucherHandler;
    private final ErrorHandler errorHandler;
    private final MemberHandler memberHandler;

    public Handler getHandler(Command command) {
        for (var handler : HANDLERS) {
            if (handler.supports(command)) {
                return handler;
            }
        }
        log.error("No handler handling command {} exist.", command);
        throw new IllegalStateException(
                "해당하는 핸들러 없습니다. 어떤 명령어도 해당되지 않을 경우 기본적으로 HELP 명령어로 전환되기에," +
                        "기본 핸들러 설정을 다시 살펴보십시오.");
    }

    @PostConstruct
    void init() {
        initializeCommandList();
        initializeHandlerList();
    }

    private void initializeCommandList() {
        for (var command : InputCommand.values()) {
            COMMANDS.put(command.getViewName(), command);
        }
        for (var command : RedirectCommand.values()) {
            COMMANDS.put(command.getViewName(), command);
        }
    }

    private void initializeHandlerList() {
        HANDLERS.add(cliHandler);
        HANDLERS.add(voucherHandler);
        HANDLERS.add(errorHandler);
        HANDLERS.add(memberHandler);
    }
}