package com.programmers.voucher.repository.customer;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;

public interface CustomerRepository {
    int save(CustomerDto customerDto);

    Customer findByEmail(String email);
}
