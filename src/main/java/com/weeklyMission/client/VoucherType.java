package com.weeklyMission.client;

import com.weeklyMission.exception.IncorrectInputException;
import java.util.Arrays;

public enum VoucherType {
    Fixed("fixed"),
    Percent("percent");

    private final String type;

    VoucherType(String type){
        this.type = type;
    }

    public static VoucherType of(String input){
        return Arrays.stream(values())
            .filter(type -> type.isEquals(input))
            .findFirst()
            .orElseThrow(() -> new IncorrectInputException("type", input, "지원하지 않는 voucher."));
    }

    private boolean isEquals(String input){
        return this.type.equals(input);
    }

    public String getType(){
        return this.type;
    }
}
