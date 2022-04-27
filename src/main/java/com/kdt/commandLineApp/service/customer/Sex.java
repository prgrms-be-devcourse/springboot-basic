package com.kdt.commandLineApp.service.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;

import java.util.Arrays;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public enum Sex {
    MEN("man"),
    WOMEN("woman");

    private final String sex;

    Sex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return sex;
    }

    public static Sex fromString(String value) throws WrongCustomerParamsException {
        return Optional.ofNullable(find(value)).orElseThrow(()-> new WrongCustomerParamsException());
    }

    private static Sex find(String value) {
        return Arrays.stream(values()).filter(sex -> sex.toString().equals(value)).findFirst().orElse(null);
    }
}
