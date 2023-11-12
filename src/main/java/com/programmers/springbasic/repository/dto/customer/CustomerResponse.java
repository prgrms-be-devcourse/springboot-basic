package com.programmers.springbasic.repository.dto.customer;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.springbasic.entity.customer.Customer;

public record CustomerResponse(UUID id, String email, String name, LocalDateTime createdAt) {

	public static CustomerResponse from(Customer customer) {
		return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getName(), customer.getCreatedAt());
	}
}
