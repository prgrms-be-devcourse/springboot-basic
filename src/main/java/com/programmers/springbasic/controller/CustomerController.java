package com.programmers.springbasic.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.programmers.springbasic.dto.GetBlacklistCustomersResponse;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.Voucher;
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

	public Customer getCustomerById(UUID customerId) {
		return customerService.getCustomerById(customerId);
	}

	public Customer updateCustomer(UUID customerId, String nameToUpdate) {
		return customerService.updateCustomer(customerId, nameToUpdate);
	}

	public Customer deleteCustomer(UUID customerId) {
		return customerService.deleteCustomer(customerId);
	}

	public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
		customerService.assignVoucherToCustomer(customerId, voucherId);
	}

	public List<Voucher> getVouchersByCustomer(UUID customerId) {
		return customerService.getVouchersByCustomer(customerId);
	}

	public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
		customerService.removeVoucherFromCustomer(customerId, voucherId);
	}

}
