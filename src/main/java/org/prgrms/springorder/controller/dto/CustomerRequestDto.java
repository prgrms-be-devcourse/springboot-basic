package org.prgrms.springorder.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;

public class CustomerRequestDto {

	private UUID customerId;
	private String name;
	private String email;
	private LocalDateTime customerCreatedAt;
	private CustomerType customerType;

	public CustomerRequestDto(UUID customerId, String name, String email, LocalDateTime customerCreatedAt,
		CustomerType customerType) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.customerCreatedAt = customerCreatedAt;
		this.customerType = customerType;
	}

	public Customer toCustomer() {
		return new Customer(this.getCustomerId(), this.getName(), this.getEmail(), this.getCustomerCreatedAt(),
			this.getCustomerType());
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

	public LocalDateTime getCustomerCreatedAt() {
		return customerCreatedAt;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}
}
