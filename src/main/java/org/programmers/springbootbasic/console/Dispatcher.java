package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.programmers.springbootbasic.console.controller.CliController;
import org.programmers.springbootbasic.console.controller.Controller;
import org.programmers.springbootbasic.console.controller.VoucherController;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.command.InputCommand.HELP;

@Slf4j
@Component
@RequiredArgsConstructor
public class Dispatcher {

    private static final Map<String, Command> commandList = new ConcurrentHashMap<>();
    private Controller controller;
    private final CliController cliController;
    private final VoucherController voucherController;
    private static final Map<String, Controller> controllerList = new ConcurrentHashMap<>();
    private final Drawer drawer;

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
        controllerList.put("cli", cliController);
        controllerList.put("voucher", voucherController);
    }

    public ConsoleResponseCode service(String input, Model model) {
        log.debug("processing input {} at dispatcher", input);

        var command = readCommand(input);
        controller = searchProperController(command);

        ModelAndView modelAndView = this.controller.process(command, model);

        ConsoleResponseCode responseCode;
        try {
            responseCode = drawer.draw(modelAndView);
        } catch (IOException e) {
            //TODO ERROR page 처리 : IOException
            responseCode = ConsoleResponseCode.ERROR;
        }
        return responseCode;
    }

    private Controller searchProperController(Command command) {
        for (Controller eachController : controllerList.values()) {
            if (eachController.supports(command)) {
                return eachController;
            }
        }
        /*일반적으로 발생할 수 없는 상황입니다.
         * 커맨드를 찾지 못한 경우 일괄적으로 단축키를 찾는 요청인 HELP 명령어가 자동 요청되기 때문입니다.
         * 아래와 같은 상황이 발생한 경우 "아무 커맨드도 찾지 못했을 때 기본적으로 주어지는 커맨드와
         * 그 커맨드에 대한 컨트롤러 연결을 확인해 봐야 합니다.
         */
        //TODO 컨트롤러 찾지 못했을 때 예외 처리
        return null;
    }

    Command readCommand(String input) {
        var command = commandList.get(input);
        return (command != null) ? command : HELP;
    }
}