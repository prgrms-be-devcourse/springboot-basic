package org.devcourse.voucher.application.customer.controller.api;

import org.devcourse.voucher.application.customer.model.Customer;

import java.util.List;

public interface CustomerController {
    Customer postCreateCustomer(String name, String email);

    List<Customer> getCustomerList();

    List<Customer> getBlackList();
}
