package com.devcourse.springbootbasic.application.converter;

import com.devcourse.springbootbasic.application.domain.customer.Customer;

import java.util.List;

public class CustomerConverter {

    public static List<String> convertToStringList(List<Customer> list) {
        return list.stream()
                .map(Customer::toString)
                .toList();
    }

    public static Customer convertCsvToCustomer(String blackCustomerInfo) {
        String[] customerInfoArray = blackCustomerInfo.split(",");
        return new Customer(Integer.parseInt(customerInfoArray[0]), customerInfoArray[1]);
    }

}
