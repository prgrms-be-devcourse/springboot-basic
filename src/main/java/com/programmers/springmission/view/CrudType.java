package com.programmers.springmission.view;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;

import java.util.Arrays;

public enum CrudType {
    CREATE("1"),
    FIND_BY_ID("2"),
    FIND_ALL("3"),
    UPDATE("4"),
    DELETE_BY_ID("5"),
    DELETE_ALL("6");

    private static final CrudType[] CRUD_TYPES = CrudType.values();

    private final String inputOption;

    CrudType(String inputOption) {
        this.inputOption = inputOption;
    }

    public String getInputOption() {
        return inputOption;
    }

    public static CrudType of(String inputOption) {
        return Arrays.stream(CRUD_TYPES)
                .filter(crudType -> crudType.inputOption.equals(inputOption))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(ErrorMessage.INVALID_OPTION_TYPE));
    }
}
