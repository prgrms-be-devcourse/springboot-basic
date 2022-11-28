package org.prgrms.springorder.controller.customer;

import java.util.List;

import org.prgrms.springorder.controller.dto.CustomerRequestDto;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.service.customer.BlackListService;
import org.prgrms.springorder.service.customer.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<String> getCustomerList() {
		return customerService.getCustomerList();
	}

	public void createCustomer(CustomerRequestDto customerRequestDto) {
		customerService.createCustomer(customerRequestDto);
	}




}
