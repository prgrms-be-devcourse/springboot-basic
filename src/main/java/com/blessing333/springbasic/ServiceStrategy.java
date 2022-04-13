package com.blessing333.springbasic;

import com.blessing333.springbasic.exception.NotSupportedStrategyException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum ServiceStrategy {
    CUSTOMER_MANAGING("1"),
    VOUCHER_MANAGING("2"),
    VOUCHER_WALLET_MANAGING("3");

    private static final Map<String, ServiceStrategy> availableServiceStrategy = initAvailableService();
    private final String optionNumber;

    private static Map<String, ServiceStrategy> initAvailableService() {
        return Collections.unmodifiableMap(
                Stream.of(values())
                        .collect(Collectors.toMap(ServiceStrategy::getOptionNumber, Function.identity()))
        );
    }

    public static ServiceStrategy fromString(String target){
        ServiceStrategy strategy = availableServiceStrategy.get(target);
        if (strategy == null)
            throw new NotSupportedStrategyException();
        else
            return strategy;
    }

}
