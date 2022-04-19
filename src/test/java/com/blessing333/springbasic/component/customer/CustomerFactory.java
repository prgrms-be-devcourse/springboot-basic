package com.blessing333.springbasic.component.customer;

import com.blessing333.springbasic.customer.domain.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerFactory {
    public static Customer createCustomer(String name, String email) {
        UUID id = UUID.randomUUID();
        return Customer.createNewCustomerWithAllArgument(id,name, email, LocalDateTime.now(),LocalDateTime.now());
    }
}
