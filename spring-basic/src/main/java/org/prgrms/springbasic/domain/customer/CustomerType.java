package org.prgrms.springbasic.domain.customer;

public enum CustomerType {

    BLACK("black"), NORMAL("normal");

    private final String value;

    CustomerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
