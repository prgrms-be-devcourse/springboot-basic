package com.program.commandLine.model.customer;

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
                .orElseThrow(() -> new IllegalArgumentException("! 잘못된 입력입니다. 바우처 타입을 숫자로 입력해주세요."));
    }

    public String getString(){
        return stringType;
    }

}
