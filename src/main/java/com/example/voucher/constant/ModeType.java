package com.example.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum ModeType {

    Exit,
    Create,
    List;

    public static ModeType getTypeMode(String typeName) {

        return Arrays.stream(ModeType.values())
            .filter(e -> typeName.equals(e.name().toLowerCase()))
            .findAny()
            .orElseThrow(
                () -> new NoSuchElementException(ConstantStrings.MESSAGE_PRINT_RETRY_VOUCHER_TYPE_SELECTION_PROMPT));
    }

}

