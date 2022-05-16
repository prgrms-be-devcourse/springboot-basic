package org.devcourse.voucher.customer.controller;

import org.devcourse.voucher.customer.model.Customer;

import java.util.List;

public interface CustomerController {
    Customer postCreateCustomer(String name, String email);

    List<Customer> getCustomerList();

    List<Customer> getBlackList();
}
