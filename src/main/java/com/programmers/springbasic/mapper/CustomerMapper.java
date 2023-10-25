package com.programmers.springbasic.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.entity.customer.Customer;

@Component
public class CustomerMapper {
	public List<Customer> linesToCustomers(List<String> fileLines) {
		return fileLines.stream().map(line -> {
			String[] parts = line.split(",");
			UUID id = UUID.fromString(parts[0]);
			String name = parts[1];
			return new Customer(id, name, null, null);
		}).collect(Collectors.toList());
	}

}
