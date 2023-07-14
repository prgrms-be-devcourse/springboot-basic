package com.programmers.springmission.view;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum CrudType {
    CREATE("1"),
    FIND_ONE("2"),
    FIND_ALL("3"),
    UPDATE("4"),
    DELETE_BY_ID("5"),
    DELETE_ALL("6"),
    WALLET("7");

    private final String inputOption;

    private static final Map<String, CrudType> CRUD_TYPE_MAP = Arrays.stream(CrudType.values())
            .collect(Collectors.toMap(CrudType::getInputOption, Function.identity()));

    public static CrudType of(String inputOption) {
        CrudType crudType = CRUD_TYPE_MAP.get(inputOption);
        if (crudType == null) {
            throw new InvalidInputException(ErrorMessage.INVALID_OPTION_TYPE);
        }

        return crudType;
    }
}
