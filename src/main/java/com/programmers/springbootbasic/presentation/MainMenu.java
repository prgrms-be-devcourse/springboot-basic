package com.programmers.springbootbasic.presentation;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_MENU;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum MainMenu {
    CREATE_VOUCHER("create voucher",ControllerAdapter::createVoucher),
    LIST_VOUCHER("list voucher",ControllerAdapter::getAllVouchers),
    LIST_BLACK_USER("list black user",ControllerAdapter::getBlackList),
    ;

    private String command;
    private BiFunction<ControllerAdapter, Object[], ConsoleResponse> function;

    MainMenu(String command, BiFunction<ControllerAdapter, Object[], ConsoleResponse> function) {
        this.command = command;
        this.function = function;
    }

    public String getCommand() {
        return command;
    }

    public ConsoleResponse execute(ControllerAdapter controller, Object... params) {
        return function.apply(controller, params);
    }

    public static ConsoleResponse routeToController(ConsoleRequest req,
        ControllerAdapter controllerAdapter){
        return Stream.of(values())
            .filter(menuCommand -> menuCommand.getCommand().equals(req.getCommand()))
            .findFirst()
            .map(menuCommand -> {
                if (req.getBody().isPresent()) {
                    return menuCommand.execute(controllerAdapter, req.getBody().get());
                } else {
                    return menuCommand.execute(controllerAdapter);
                }
            })
            .orElseThrow(() -> new CustomException(INVALID_MENU));
    }
}
