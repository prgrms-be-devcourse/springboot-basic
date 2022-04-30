package org.devcourse.voucher.customer.service;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer createCustomer(UUID customerId, String name, Email email);

    List<Customer> recallAllCustomer();
}
