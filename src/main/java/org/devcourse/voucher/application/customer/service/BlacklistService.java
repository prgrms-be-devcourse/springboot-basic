package org.devcourse.voucher.application.customer.service;

import org.devcourse.voucher.application.customer.model.Customer;

import java.util.List;

public interface BlacklistService {
    List<Customer> recallAllBlacklist();
}
