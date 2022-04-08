package org.prgrms.springbootbasic;

public enum Menu {
    CREATE, LIST, EXIT, BLACKLIST;

    public boolean isExit() {
        return this == EXIT;
    }

    public boolean isCreate() {
        return this == CREATE;
    }

    public boolean isList() {
        return this == LIST;
    }

    public boolean isBlackList() {
        return this == BLACKLIST;
    }
}
