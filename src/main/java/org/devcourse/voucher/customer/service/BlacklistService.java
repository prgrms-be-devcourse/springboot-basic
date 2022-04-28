package org.devcourse.voucher.customer.service;

import org.devcourse.voucher.customer.Customer;

import java.util.List;

public interface BlacklistService {
    List<Customer> recallAllBlacklist();
}
