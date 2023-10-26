package com.programmers.springbasic.entity.customer;

import static com.programmers.springbasic.constants.ErrorCode.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

public class Customer {

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	private static final String NAME_REGEX = "^[a-zA-Z가-힣\\s]+$";

	private final UUID id;
	private final String email;
	private final LocalDateTime createdAt;
	private String name;

	public Customer(UUID id, String name, String email, LocalDateTime createdAt) {
		validateName(name);
		validateEmail(email);
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
		validateName(nameToUpdate);
		this.name = nameToUpdate;
	}

	private void validateEmail(String email) {
		if (!Pattern.matches(EMAIL_REGEX, email)) {
			throw new IllegalArgumentException(INVALID_EMAIL.getMessage());
		}
	}

	private void validateName(String name) {
		if (!Pattern.matches(NAME_REGEX, name)) {
			throw new IllegalArgumentException(INVALID_NAME.getMessage());
		}
	}

}
