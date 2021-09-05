package org.prgrms.kdt.mapper;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerDto;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 1:15 오후
 */
public interface CustomerMapper {

    static CustomerDto customerToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setCustomerType(customer.getCustomerType() == null ? null : customer.getCustomerType().name());
        customerDto.setCreatedAt(customer.getCreatedAt());
        customerDto.setLastLoginAt(customer.getLastLoginAt() == null ? null : customer.getLastLoginAt());
        return customerDto;
    }
}
