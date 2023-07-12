package com.programmers.springbootbasic.customer.domain;

public enum CustomerType {
    NORMAL("normal"),
    BLACK("black");

    private final String name;

    CustomerType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
