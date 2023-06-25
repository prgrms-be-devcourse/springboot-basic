package org.devcourse.springbasic.menu;

public enum RunLogicBean {

    CREATE_LOGIC_BEAN(
            RunByCreatesMenu.class.getSimpleName().substring(0, 1).toLowerCase()
            + RunByCreatesMenu.class.getSimpleName().substring(1)),

    PRINT_HISTORY_LOGIC_BEAN(
            RunByHistoryMenu.class.getSimpleName().substring(0, 1).toLowerCase()
            + RunByHistoryMenu.class.getSimpleName().substring(1));


    private final String name;

    RunLogicBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
