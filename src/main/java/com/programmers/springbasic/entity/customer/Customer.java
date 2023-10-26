package com.programmers.springbasic.entity.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

	private final UUID id;
	private final String email;
	private final LocalDateTime createdAt;
	private String name;

	public Customer(UUID id, String name, String email, LocalDateTime createdAt) {
		// todo : validate
		this.id = id;
		this.name = name;
		this.email = email;
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void changeName(String nameToUpdate) {
		this.name = nameToUpdate;
	}

}
