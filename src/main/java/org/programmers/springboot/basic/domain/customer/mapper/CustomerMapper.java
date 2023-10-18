package org.programmers.springboot.basic.domain.customer.mapper;

import org.programmers.springboot.basic.config.CustomerConfig;
import org.programmers.springboot.basic.domain.customer.dto.CsvCustomerDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerMapper {

    private final CustomerConfig customerConfig;

    public CustomerMapper(CustomerConfig customerConfig) {
        this.customerConfig = customerConfig;
    }

    public CsvCustomerDto deserialize(String[] token) {

        UUID customerId = UUID.fromString(token[0]);
        String name = token[1];
        CustomerType customerType = CustomerType.valueOf(token[2]);

        return this.customerConfig.getCsvCustomerDto(customerId, name, customerType);
    }

    public Customer mapToCustomer(CsvCustomerDto customerDto) {
        return this.customerConfig.getCustomer(customerDto);
    }
}
