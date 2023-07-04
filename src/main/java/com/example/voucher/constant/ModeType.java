package com.example.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum ModeType {

    EXIT,
    CREATE,
    LIST;

    public static ModeType getTypeMode(String typeName) {
        try {
            return ModeType.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(ConstantStrings.MESSAGE_PRINT_RETRY_VOUCHER_TYPE_SELECTION_PROMPT);
        }
    }

}

