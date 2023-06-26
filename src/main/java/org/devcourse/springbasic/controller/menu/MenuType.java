package org.devcourse.springbasic.controller.menu;

import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.util.DigitChecker;
import org.devcourse.springbasic.service.VoucherService;

import java.util.Arrays;
import java.util.function.BiFunction;


public enum MenuType {

    EXIT(1, RunByExitMenu::new),
    CREATE(2, RunByCreatesMenu::new),
    LIST(3, RunByHistoryMenu::new);

    private final int inputNum;
    private final BiFunction<IODevice, VoucherService, RunByMenu> FunctionToRunByMenu;

    MenuType(int inputNum, BiFunction<IODevice, VoucherService, RunByMenu> FunctionToRunByMenu) {
        this.inputNum = inputNum;
        this.FunctionToRunByMenu = FunctionToRunByMenu;
    }

    public BiFunction<IODevice, VoucherService, RunByMenu> getFunctionToRunByMenu() {
        return FunctionToRunByMenu;
    }

    public static MenuType findSolveStateByInput(String input) {

        if (DigitChecker.isDigit(input)) {
            return Arrays.stream(MenuType.values())
                    .filter(solveState -> (solveState.inputNum == Integer.parseInt(input)) && solveState.inputNum != 0)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));
        } else {
            return MenuType.valueOf(input.toUpperCase());
        }
    }

    public boolean isExit() {
        return this == EXIT;
    }
}
