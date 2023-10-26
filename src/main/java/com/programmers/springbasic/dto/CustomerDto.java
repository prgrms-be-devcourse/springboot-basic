package com.programmers.springbasic.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.springbasic.entity.customer.Customer;

public record CustomerDto(UUID id, String email, String name, LocalDateTime createdAt) {

	public static CustomerDto from(Customer customer) {
		return new CustomerDto(customer.getId(), customer.getEmail(), customer.getName(), customer.getCreatedAt());
	}
}
