package org.prgrms.kdt.domain.customer.model;

import java.util.Arrays;

public enum CustomerType {
    NORMAL("NORMAL"),
    BLACK_LIST("BLACK");

    private final String type;

    CustomerType(String type) {
        this.type = type;
    }

    public static CustomerType findCustomerType(String type){
        return Arrays.stream(CustomerType.values())
                .filter(value -> value.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 고객타입 입니다."));
    }

    public String getType(){
        return type;
    }
}
