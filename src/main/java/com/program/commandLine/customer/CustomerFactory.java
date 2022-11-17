package com.program.commandLine.customer;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomerFactory {

    public Customer createCustomer(CustomerType customerType,UUID customerId, String name, String email){
        Customer newCustomer = switch (customerType){
            case REGULAR_CUSTOMER -> new RegularCustomer(customerId,name,email, LocalDateTime.now());
            case BLACK_LIST_CUSTOMER -> new BlackListCustomer(customerId,name,email,LocalDateTime.now());
        };
        return newCustomer;
    }

    public Customer createCustomer(CustomerType customerType,UUID customerId, String name, String email,LocalDateTime lastLoginAt,LocalDateTime createdAt){
        Customer newCustomer = switch (customerType){
            case REGULAR_CUSTOMER -> new RegularCustomer(customerId,name,email, LocalDateTime.now());
            case BLACK_LIST_CUSTOMER -> new BlackListCustomer(customerId,name,email,LocalDateTime.now());
        };
        return newCustomer;
    }

}
