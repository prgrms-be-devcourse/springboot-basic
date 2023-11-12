package com.programmers.vouchermanagement.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.vouchermanagement.customer.controller.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.domain.Customer;

import java.util.List;

public class MvcControllerResource {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final List<CustomerResponse> CUSTOMERS = List.of(
            CustomerResponse.from(new Customer("customer1", false)),
            CustomerResponse.from(new Customer("customer2", true))
    );
    public static final List<CustomerResponse> BLACKLIST = List.of(
            CustomerResponse.from(new Customer("customer3", true)),
            CustomerResponse.from(new Customer("customer4", true))
    );
    public static final CreateCustomerRequest CREATE_CUSTOMER_REQUEST = new CreateCustomerRequest("customer5", true);

    public static final Customer CUSTOMER = new Customer(CREATE_CUSTOMER_REQUEST.name(), CREATE_CUSTOMER_REQUEST.isBlack());
    public static final CustomerResponse CUSTOMER_RESPONSE = CustomerResponse.from(CUSTOMER);
}
