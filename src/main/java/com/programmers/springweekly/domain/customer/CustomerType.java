package com.programmers.springweekly.domain.customer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CustomerType {
    NORMAL("normal"),
    BLACKLIST("blacklist");

    private final String type;
    private static final Map<String, CustomerType> CUSTOMER_MAP =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(CustomerType::getCustomerType, Function.identity())));

    CustomerType(String type) {
        this.type = type;
    }

    public static CustomerType findCustomerType(String type){
       if(CUSTOMER_MAP.containsKey(type)){
           return CUSTOMER_MAP.get(type);
       }

       throw new IllegalArgumentException("The type you are looking for is not found.");
    }

    private String getCustomerType(){
        return type;
    }
}
