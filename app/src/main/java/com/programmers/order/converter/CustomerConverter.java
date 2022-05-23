package com.programmers.order.converter;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.programmers.order.controller.dto.CustomerDto;
import com.programmers.order.domain.Customer;

@Component
public class CustomerConverter {

	public Converter<CustomerDto.Create, Customer> createDtoToDomain() {
		return createDto -> Customer.builder()
				.customerId(UUID.randomUUID())
				.email(createDto.email())
				.name(createDto.name())
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();

	}

	public Converter<Customer, CustomerDto.Response> domainToResponse() {
		return customer -> CustomerDto.Response.builder()
				.customerId(customer.getCustomerId())
				.email(customer.getEmail())
				.name(customer.getName())
				.createdAt(customer.getCreatedAt())
				.updatedAt(customer.getUpdatedAt())
				.build();
	}
}
