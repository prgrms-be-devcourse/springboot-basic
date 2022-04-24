package org.prgms.springbootbasic.customer.entity;

import java.util.UUID;

import lombok.Getter;

/**
 * 고객 엔터티
 */
@Getter
public class Customer {
	/* 고객 식별번호  필수*/
	private final UUID customerId;
	/* 고객 이름*/
	private String name;
	/* 고객 email 필수*/
	private final String email;
	/* 고객 상태 */
	private CustomerStatus status;

	public Customer(UUID customerId, String name, String email,
		CustomerStatus status) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.status = status;
	}

	public Customer(String name, String email) {
		this(UUID.randomUUID(), name, email, CustomerStatus.AVAILABLE);
	}

}
