package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum Sex {
    MEN("man"),
    WOMEN("woman");

    private final String sex;
    private static final Map<String, Sex> sexHashMap = Stream.of(values()).collect(toMap(Object::toString, (e)-> e));

    Sex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return sex;
    }

    public static Sex fromString(String sex) throws WrongCustomerParamsException {
        return Optional.ofNullable(sexHashMap.get(sex)).orElseThrow(()-> new WrongCustomerParamsException());
    }
}
