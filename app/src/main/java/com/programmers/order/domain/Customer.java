package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.dto.CustomerDto;
import com.programmers.order.exception.DomainException;
import com.programmers.order.message.ErrorMessage;

public class Customer {
	private final UUID customerId;
	private String name;
	private final String email;
	private LocalDateTime lastLoginAt;
	private final LocalDateTime createdAt;

	public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
	}

	public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.lastLoginAt = lastLoginAt;
		this.createdAt = createdAt;
	}

	public Customer changeName(CustomerDto.UpdateCustomer updateCustomer) {
		validateName(updateCustomer.getNameToChange());
		this.name = updateCustomer.getNameToChange();
		return this;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	private void validateName(String name) {
		if (name.isBlank()) {
			throw new DomainException.NotEmptyField(ErrorMessage.CLIENT_ERROR);
		}
	}

}
