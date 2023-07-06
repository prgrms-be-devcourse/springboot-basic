package com.example.voucher.controller;

import java.util.NoSuchElementException;

import com.example.voucher.constant.ConstantStrings;

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

