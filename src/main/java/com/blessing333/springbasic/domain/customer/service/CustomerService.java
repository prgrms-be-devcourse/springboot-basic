package com.blessing333.springbasic.domain.customer.service;

import com.blessing333.springbasic.domain.customer.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    Customer inquiryCustomerInformation(UUID id);
    List<Customer> loadAllCustomerInformation();
}
