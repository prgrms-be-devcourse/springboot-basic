package org.devcourse.voucher.customer.controller;

import org.devcourse.voucher.customer.model.Customer;

public interface CustomerController {
    Customer createCustomerMapper(String name, String email);
}
