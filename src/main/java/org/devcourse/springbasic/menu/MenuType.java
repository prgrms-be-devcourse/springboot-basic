package org.devcourse.springbasic.menu;

import org.devcourse.springbasic.util.DigitChecker;

import java.util.Arrays;

public enum MenuType {

    NONE(0, ""),
    EXIT(1, ""),
    CREATE(2, RunLogicBean.CREATE_LOGIC_BEAN.getName()),
    LIST(3, RunLogicBean.PRINT_HISTORY_LOGIC_BEAN.getName());

    private final int inputNum;
    private final String beanName;

    MenuType(int inputNum, String beanName) {
        this.inputNum = inputNum;
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
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

}
