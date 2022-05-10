package com.kdt.commandLineApp.service.customer;

public class CustomerConverter {
    public static CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAge(), customer.getSex());
    }
}
