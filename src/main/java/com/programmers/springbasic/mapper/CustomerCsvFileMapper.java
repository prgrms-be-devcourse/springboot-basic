package com.programmers.springbasic.mapper;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.entity.customer.Customer;

@Component
public class CustomerCsvFileMapper {
	public List<Customer> linesToCustomers(List<String> fileLines) {
		return fileLines.stream().map(line -> {
			String[] parts = line.split(",");
			UUID id = UUID.fromString(parts[0]);
			String name = parts[1];
			String email = parts[2];
			return new Customer(id, name, email, null);
		}).toList();
	}

}
