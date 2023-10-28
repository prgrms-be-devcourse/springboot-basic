package com.programmers.springbootbasic.presentation;

import static com.programmers.springbootbasic.exception.ErrorCode.INVALID_MENU;

import com.programmers.springbootbasic.exception.exceptionClass.CustomException;
import com.programmers.springbootbasic.mediator.ConsoleRequest;
import com.programmers.springbootbasic.mediator.ConsoleResponse;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public enum MainMenu {
    EXIT("exit", ControllerAdapter::handleExit),
    CREATE_VOUCHER("create voucher", ControllerAdapter::createVoucher),
    FIND_ALL_VOUCHER("list voucher", ControllerAdapter::getAllVouchers),
    FIND_VOUCHER("find voucher", ControllerAdapter::getVoucherById),
    DELETE_VOUCHER("delete voucher", ControllerAdapter::deleteVoucherById),
    UPDATE_VOUCHER("update voucher", ControllerAdapter::updateVoucher),
    LIST_BLACK_USER("list black user", ControllerAdapter::getBlackList),
    CREATE_USER_VOUCHER("register my voucher", ControllerAdapter::createUserVoucher),
    FIND_USER_BY_VOUCHER("find users by voucher Id", ControllerAdapter::findUserByVoucherId),
    FIND_VOUCHER_MINE("find my voucher", ControllerAdapter::findVoucherByUserNickname),
    DELETE_VOUCHER_MINE("delete my voucher", ControllerAdapter::deleteUserVoucherById),
    ;

    private final String command;
    private final BiFunction<ControllerAdapter, Object[], ConsoleResponse> function;

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

    public static ConsoleResponse routeToController(
        ConsoleRequest req,
        ControllerAdapter controllerAdapter
    ) {
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
