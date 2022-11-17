package com.program.commandLine.customer;

import java.util.Arrays;
import java.util.Objects;

public enum CustomerType {
    BLACK_LIST_CUSTOMER("black_list"),
    REGULAR_CUSTOMER("regular");

    private final String stringType;
    CustomerType(String stringType) {
        this.stringType = stringType;
    }

    public static CustomerType getType(String stringType){
        return Arrays.stream(values())
                .filter(type -> Objects.equals(type.stringType,stringType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("! Wrong type input. Please enter only given number"));
    }

    public String getString(){
        return stringType;
    }

}
