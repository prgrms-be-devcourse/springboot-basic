package org.prgrms.springorder.controller;

import org.prgrms.springorder.io.IO;
import org.prgrms.springorder.service.BlackListService;
import org.prgrms.springorder.service.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

	private final CustomerService customerService;
	private final BlackListService blackListService;
	private final IO io;

	public CustomerController(CustomerService customerService,
		BlackListService blackListService, IO io) {
		this.customerService = customerService;
		this.blackListService = blackListService;
		this.io = io;
	}

	public void showBlackList() {
		io.writeList(blackListService.findAll());
	}


}
