package org.programmers.springorder.customer.dto;

import org.programmers.springorder.customer.model.CustomerType;
import org.springframework.lang.NonNull;

public record CustomerRequestDto(
        @NonNull String name,
        @NonNull CustomerType customerType) {
}
