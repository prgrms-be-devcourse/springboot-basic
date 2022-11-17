package com.programmers.voucher.repository.customer;

import com.programmers.voucher.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    int save(CustomerDto customerDto);

    List<Customer> findAllBlack();
}
