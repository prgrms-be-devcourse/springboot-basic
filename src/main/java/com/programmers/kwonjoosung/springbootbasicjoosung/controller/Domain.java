package com.programmers.kwonjoosung.springbootbasicjoosung.controller;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongCommandException;

import java.util.Objects;
import java.util.stream.Stream;

public enum Domain {

    VOUCHER("voucher"),
    CUSTOMER("customer"),
    WALLET("wallet"),
    HELP("help"),
    EXIT("exit");

    private final String domain;

    Domain(String domainType) {
        this.domain = domainType;
    }

    public static Domain of(String domainType) {
        return Stream.of(Domain.values())
                .filter(value -> Objects.equals(value.domain,domainType.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new WrongCommandException(domainType));
    }
    
}
