package org.prgrms.kdt.app.command;

public enum CommandType {
    EXIT("0"),
    VOUCHER_CREATE("1"),
    VOUCHER_LIST("2"),
    DELETE_ALL_VOUCHER("3"),
    UPDATE_VOUCHER_TYPE("4"),
    CUSTOMER_LIST("5"),
    BLACK_LIST("6");

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