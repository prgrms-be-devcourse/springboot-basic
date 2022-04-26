package com.programmers.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.domain.Customer;

public class CustomerDto {

	public static class SaveRequestDto {
		private final String name;
		private final String email;
		private final LocalDateTime createdAt;

		public SaveRequestDto(String name, String email, LocalDateTime createdAt) {
			this.name = name;
			this.email = email;
			this.createdAt = createdAt;
		}

		public Customer toCustomer() {
			return new Customer(UUID.randomUUID(), this.email, this.email, LocalDateTime.now());
		}

	}

	public static class UpdateCustomer {
		private final String email;
		private final String nameToChange;

		public UpdateCustomer(String email, String nameToChange) {
			this.email = email;
			this.nameToChange = nameToChange;
		}

		public String getEmail() {
			return email;
		}

		public String getNameToChange() {
			return nameToChange;
		}
	}

}
