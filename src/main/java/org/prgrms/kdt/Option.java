package org.prgrms.kdt;

public enum Option {
    EXIT("EXIT"), LIST("LIST"), CREATE("CREATE");

    private String matchString;

    Option(String matchString) {
        this.matchString = matchString;
    }
}
