package org.devcourse.voucher.application.customer.service;

import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(String name, Email email);

    List<Customer> recallAllCustomer();
}
