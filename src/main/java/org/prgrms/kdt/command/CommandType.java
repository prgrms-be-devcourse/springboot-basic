package org.prgrms.kdt.command;

public enum CommandType {
    EXIT("0"),
    VOUCHER_CREATE("1"),
    VOUCHER_LIST("2");

    private final String num;

    CommandType(String value) {
        this.num = value;
    }

    public String getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "%s. %s".formatted(this.getNum(), super.toString());
    }

}