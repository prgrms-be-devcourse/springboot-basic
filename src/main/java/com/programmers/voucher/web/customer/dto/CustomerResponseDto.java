package com.programmers.voucher.web.customer.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.programmers.voucher.domain.customer.model.CustomerType;

public class CustomerResponseDto {

	private UUID customerId;
	private LocalDateTime createdAt;
	private CustomerType customerType;
	private LocalDateTime lastModifiedAt;

	public CustomerResponseDto(UUID customerId, LocalDateTime createdAt,
		CustomerType customerType, LocalDateTime lastModifiedAt) {
		this.customerId = customerId;
		this.createdAt = createdAt;
		this.customerType = customerType;
		this.lastModifiedAt = lastModifiedAt;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public String getCreatedAt() {
		return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public String getLastModifiedAt() {
		return lastModifiedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
