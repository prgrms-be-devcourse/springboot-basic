package com.programmers.springbasic.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.programmers.springbasic.dto.CustomerDto;
import com.programmers.springbasic.dto.VoucherDto;
import com.programmers.springbasic.service.CustomerService;

@Controller
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<CustomerDto> getBlacklistCustomers() {
		return customerService.getBlacklistCustomers();
	}

	public CustomerDto createCustomer(String name, String email) {
		return customerService.createCustomer(name, email);
	}

	public List<CustomerDto> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	public CustomerDto getCustomerById(UUID customerId) {
		return customerService.getCustomerById(customerId);
	}

	public CustomerDto updateCustomer(UUID customerId, String nameToUpdate) {
		return customerService.updateCustomer(customerId, nameToUpdate);
	}

	public CustomerDto deleteCustomer(UUID customerId) {
		return customerService.deleteCustomer(customerId);
	}

	public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
		customerService.assignVoucherToCustomer(customerId, voucherId);
	}

	public List<VoucherDto> getVouchersByCustomer(UUID customerId) {
		return customerService.getVouchersByCustomer(customerId);
	}

	public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
		customerService.removeVoucherFromCustomer(customerId, voucherId);
	}

}
