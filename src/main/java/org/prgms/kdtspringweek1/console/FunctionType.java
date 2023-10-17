package org.prgms.kdtspringweek1.console;


public enum FunctionType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("blackList");

    private String name;

    FunctionType(String name) {
        this.name = name;
    }

    public static FunctionType getValueByName(String name) {
        for (FunctionType functionType : FunctionType.values()) {
            if (functionType.name.equals(name)) {
                return functionType;
            }
        }
    }
}
