package com.programmers.springbasic.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.programmers.springbasic.controller.dto.ListBlacklistCustomerResponse;
import com.programmers.springbasic.service.CustomerService;

@Controller
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<ListBlacklistCustomerResponse> listBlacklistCustomer() {
		return customerService.listBlacklistCustomer();
	}
}
