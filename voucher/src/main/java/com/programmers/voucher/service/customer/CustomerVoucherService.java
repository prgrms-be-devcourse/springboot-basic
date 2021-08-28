package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;

import java.util.Optional;

public interface CustomerVoucherService extends CustomerService {
    Optional<Customer> findByVoucher(long voucherId);
}
