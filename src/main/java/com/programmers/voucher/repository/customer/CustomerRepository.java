package com.programmers.voucher.repository.customer;

import com.programmers.voucher.controller.dto.CustomerRequest;
import com.programmers.voucher.model.customer.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(CustomerRequest customerRequest);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByVoucher(UUID voucherId);
}

