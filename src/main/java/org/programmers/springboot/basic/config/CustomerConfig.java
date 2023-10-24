package org.programmers.springboot.basic.config;

import org.programmers.springboot.basic.domain.customer.dto.CsvCustomerDto;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerConfig {
    public Customer getCustomer(CsvCustomerDto csvCustomerDto) {
        return new Customer(csvCustomerDto.customerId(), csvCustomerDto.name(), csvCustomerDto.customerType());
    }

    public CustomerResponseDto getCustomerResponseDto(Customer customer) {
        return new CustomerResponseDto(customer.getCustomerId(), customer.getName(), customer.getCustomerType());
    }

    public CsvCustomerDto getCsvCustomerDto(UUID customerId, String name, CustomerType customerType) {
        return new CsvCustomerDto(customerId, name, customerType);
    }
}
