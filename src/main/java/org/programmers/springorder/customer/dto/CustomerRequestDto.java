package org.programmers.springorder.customer.dto;

import org.programmers.springorder.customer.model.CustomerType;

public record CustomerRequestDto(String name, CustomerType customerType) {
}
