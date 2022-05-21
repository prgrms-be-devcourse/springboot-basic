package com.voucher.vouchermanagement.utils.deserializer;

import java.time.LocalDateTime;
import java.util.StringTokenizer;
import java.util.UUID;

import com.voucher.vouchermanagement.domain.customer.model.Customer;

public class CustomerCsvMapper implements CsvMapper<Customer> {
	@Override
	public Customer deserialize(String csvLine) {
		StringTokenizer stringTokenizer = new StringTokenizer(csvLine, ",");
		String id = stringTokenizer.nextToken().trim();
		String name = stringTokenizer.nextToken().trim();
		String email = stringTokenizer.nextToken().trim();
		LocalDateTime lastLoginAt = LocalDateTime.parse(stringTokenizer.nextToken());
		LocalDateTime createdAt = LocalDateTime.parse(stringTokenizer.nextToken());

		return new Customer(UUID.fromString(id), name, email, lastLoginAt, createdAt);
	}

	@Override
	public String serialize(Customer target) {
		return String.join(",", target.getId().toString(), target.getName(), target.getEmail());
	}
}
