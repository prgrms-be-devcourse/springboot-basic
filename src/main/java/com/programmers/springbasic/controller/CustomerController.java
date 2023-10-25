package com.programmers.springbasic.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.programmers.springbasic.dto.GetBlacklistCustomersResponse;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.service.CustomerService;

@Controller
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<GetBlacklistCustomersResponse> getBlacklistCustomers() {
		return customerService.getBlacklistCustomers();
	}

	public Customer createCustomer(String name, String email) {
		return customerService.createCustomer(name, email);
	}

	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	public Customer getCustomerById(String customerId) {
		return customerService.getCustomerById(UUID.fromString(customerId));
	}

	public Customer updateCustomer(String customerId, String nameToUpdate) {
		return customerService.updateCustomer(customerId,nameToUpdate);
	}

	public Customer deleteCustomer(String customerId) {
		return customerService.deleteCustomer(customerId);
	}
}
