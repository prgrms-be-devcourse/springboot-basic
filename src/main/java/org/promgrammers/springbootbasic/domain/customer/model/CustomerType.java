package org.promgrammers.springbootbasic.domain.customer.model;

public enum CustomerType {
    WHITE("white"),
    BLACK("black");

    private final String typeName;

    CustomerType(String typeName) {
        this.typeName = typeName;
    }
}
