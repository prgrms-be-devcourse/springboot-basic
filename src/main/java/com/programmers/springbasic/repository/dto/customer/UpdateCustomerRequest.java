package com.programmers.springbasic.repository.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record UpdateCustomerRequest(@NotBlank String nameToUpdate) {
}
