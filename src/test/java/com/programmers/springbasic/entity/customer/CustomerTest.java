package com.programmers.springbasic.entity.customer;

import static com.programmers.springbasic.constants.ErrorCode.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class CustomerTest {

	@Test
	void 유효한_고객을_생성한다() {
		UUID id = UUID.randomUUID();
		String name = "홍길동";
		String email = "hong@example.com";
		LocalDateTime createdAt = LocalDateTime.now();

		Customer customer = new Customer(id, name, email, createdAt);

		assertThat(customer.getId(), is(id));
		assertThat(customer.getName(), is(name));
		assertThat(customer.getEmail(), is(email));
		assertThat(customer.getCreatedAt(), is(createdAt));
	}

	@Test
	void 유효하지_않은_이메일이면_예외가_발생한다() {
		UUID id = UUID.randomUUID();
		String name = "홍길동";
		String invalidEmail = "invalidemail";
		LocalDateTime createdAt = LocalDateTime.now();

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			new Customer(id, name, invalidEmail, createdAt)
		);
		assertThat(exception.getMessage(), is(INVALID_EMAIL.getMessage()));
	}

	@Test
	void 유효하지_않은_이름이면_예외가_발생한다() {
		UUID id = UUID.randomUUID();
		String invalidName = "홍길동123";
		String email = "hong@example.com";
		LocalDateTime createdAt = LocalDateTime.now();

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			new Customer(id, invalidName, email, createdAt)
		);
		assertThat(exception.getMessage(), is(INVALID_NAME.getMessage()));
	}

	@Test
	void 고객의_이름을_수정한다() {
		UUID id = UUID.randomUUID();
		String originalName = "홍길동";
		String newName = "새로운홍길동";
		String email = "hong@example.com";
		LocalDateTime createdAt = LocalDateTime.now();
		Customer customer = new Customer(id, originalName, email, createdAt);

		customer.changeName(newName);

		assertThat(customer.getName(), is(newName));
	}

	@Test
	void 유효하지_않은_이름이면_수정시_예외가_발생한다() {
		UUID id = UUID.randomUUID();
		String originalName = "홍길동";
		String invalidNewName = "홍길동123";
		String email = "hong@example.com";
		LocalDateTime createdAt = LocalDateTime.now();
		Customer customer = new Customer(id, originalName, email, createdAt);

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
			customer.changeName(invalidNewName)
		);
		assertThat(exception.getMessage(), is(INVALID_NAME.getMessage()));
	}
}
