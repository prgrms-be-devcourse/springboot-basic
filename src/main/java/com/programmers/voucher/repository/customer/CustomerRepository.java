package com.programmers.voucher.repository.customer;

import com.programmers.voucher.dto.CustomerDto;

public interface CustomerRepository {
    int save(CustomerDto customerDto);
}
