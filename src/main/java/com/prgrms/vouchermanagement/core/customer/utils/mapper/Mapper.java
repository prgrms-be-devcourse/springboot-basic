package com.prgrms.vouchermanagement.core.customer.utils.mapper;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import com.prgrms.vouchermanagement.core.customer.dto.CustomerDto;

public class Mapper {

    public static Customer toCustomer(CustomerDto customerDto) {
        return new Customer(customerDto.getName(), customerDto.getEmail());
    }
}
