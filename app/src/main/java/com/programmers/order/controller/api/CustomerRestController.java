package com.programmers.order.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.order.controller.dto.CustomerDto;
import com.programmers.order.converter.CustomerConverter;
import com.programmers.order.domain.Customer;
import com.programmers.order.service.CustomerService;

@RequestMapping("/customer")
@RestController
public class CustomerRestController {

	private final CustomerService customerService;
	private final CustomerConverter customerConverter;

	public CustomerRestController(CustomerService customerService, CustomerConverter customerConverter) {
		this.customerService = customerService;
		this.customerConverter = customerConverter;
	}

	@PostMapping("")
	public CustomerDto.Response create(CustomerDto.Create createDto) {
		Customer requestCustomer = customerConverter.createDtoToDomain().convert(createDto);
		Customer customer = customerService.create(requestCustomer);

		return customerConverter.domainToResponse().convert(customer);
	}
}
