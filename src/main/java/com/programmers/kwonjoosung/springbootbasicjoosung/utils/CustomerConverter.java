package com.programmers.kwonjoosung.springbootbasicjoosung.utils;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;

import java.util.UUID;

public class CustomerConverter {
    public static Customer toCustomer(String text) {
        String[] textCustomer = text.split(",");
        return new Customer(UUID.fromString(textCustomer[0]), textCustomer[1]);
    }

}
