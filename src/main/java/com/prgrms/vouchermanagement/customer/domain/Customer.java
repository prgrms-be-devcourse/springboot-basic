package com.prgrms.vouchermanagement.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
	private final UUID customerId;
	private String name;
	private final String email;
	private final LocalDateTime createdAt;
	private LocalDateTime lastLoginAt;

	// TODO : 이메일이나 이름에 대한 요구사항이 존재할 경우 -> email 이나 name 에 대한 검증 필요할 것
	public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
		this(customerId, name, email, null, createdAt);
	}

	public Customer(UUID customerId, String name, String email) {
		this(customerId, name, email, null, LocalDateTime.now());
	}

	public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
		this.validateName(name);
		this.validateEmail(email);
		this.checkNull(customerId, "customerId 는 null 이 올 수 없습니다");
		this.checkNull(createdAt, "createdAt 는 null 이 올 수 없습니다");

		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.lastLoginAt = lastLoginAt;
		this.createdAt = createdAt;
	}

	private void checkNull(Object obj, String message){
		if(obj == null){
			throw new IllegalArgumentException(message);
		}
	}

	private void validateName(String name) {
		if (name.isBlank()) {
			throw new IllegalArgumentException("name 은 공백이 올 수 없습니다");
		}
	}

	private void validateEmail(String email) {
		if (email.isBlank()) {
			throw new IllegalArgumentException("email 은 공백이 올 수 없습니다");
		}
	}

	public void changeName(String name) {
		this.validateName(name);
		this.name = name;
	}

	public void login() {
		this.lastLoginAt = LocalDateTime.now();
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

}
