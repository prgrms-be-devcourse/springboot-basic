package org.programmers.springboot.basic.domain.customer.dto;

import org.programmers.springboot.basic.domain.customer.entity.CustomerType;

import java.util.UUID;

public record CsvCustomerDto(UUID customerId, String name, CustomerType customerType) {
}
