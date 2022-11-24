package com.programmers.voucher.repository.customer;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(CustomerDto customerDto);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByVoucher(UUID voucherId);
}

