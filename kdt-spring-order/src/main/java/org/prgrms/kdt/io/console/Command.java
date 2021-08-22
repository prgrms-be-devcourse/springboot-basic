package org.prgrms.kdt.io.console;

public enum Command {
    EXIT("EXIT"), CONTINUE("CONTINUE"), LIST("LIST"), CREATE("CREATE");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
