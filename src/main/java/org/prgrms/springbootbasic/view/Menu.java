package org.prgrms.springbootbasic.view;

public enum Menu {
    CREATE, LIST, EXIT;

    public boolean isExit() {
        return this == EXIT;
    }

    public boolean isCreate() {
        return this == CREATE;
    }

    public boolean isList() {
        return this == LIST;
    }
}
