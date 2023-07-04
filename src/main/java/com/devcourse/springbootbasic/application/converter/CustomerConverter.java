package com.devcourse.springbootbasic.application.converter;

import com.devcourse.springbootbasic.application.domain.customer.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public final class CustomerConverter {

    private CustomerConverter() {}

    public static List<String> convertToStringList(List<Customer> list) {
        return list.stream()
                .map(Customer::toString)
                .toList();
    }

    public static Customer convertCsvToCustomer(String blackCustomerInfo) {
        String[] customerInfoArray = blackCustomerInfo.split(",");
        return new Customer(
                UUID.fromString(customerInfoArray[0]),
                customerInfoArray[1],
                customerInfoArray[2],
                LocalDateTime.parse(customerInfoArray[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        );
    }

}
