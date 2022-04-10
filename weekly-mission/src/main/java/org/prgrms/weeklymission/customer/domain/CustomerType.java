package org.prgrms.weeklymission.customer.domain;

public enum CustomerType {
    BLACK("black"), NORMAL("normal");

    private final String value;
    CustomerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    };
}
