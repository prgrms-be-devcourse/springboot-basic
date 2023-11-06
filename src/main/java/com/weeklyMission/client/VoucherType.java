package com.weeklyMission.client;

import com.weeklyMission.exception.ExceptionMessage;
import com.weeklyMission.exception.IncorrectInputException;
import java.util.Arrays;
import lombok.Getter;

@Getter
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
            .orElseThrow(() -> new IncorrectInputException("type", input, ExceptionMessage.BAD_REQUEST_VOUCHER.getMessage()));
    }

    private boolean isEquals(String input){
        return this.type.equals(input);
    }
}
