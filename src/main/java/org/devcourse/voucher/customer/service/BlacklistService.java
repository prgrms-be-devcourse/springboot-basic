package org.devcourse.voucher.customer.service;

import org.devcourse.voucher.customer.model.Customer;

import java.util.List;

public interface BlacklistService {
    List<Customer> recallAllBlacklist();
}
