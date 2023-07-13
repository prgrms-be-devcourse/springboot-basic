package co.programmers.voucher_management.customer.controller;

import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.service.CustomerService;

@Controller
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Response executeCustomerMenu(String commandNum) {
		if (commandNum.equals("1")) {
			return inquiryBlackList();
		}
		return new Response(Response.State.FAILED, "Unsupported menu");
	}

	public Response inquiryBlackList() {
		String rating = Customer.Rating.BLACKLIST.toString();
		return customerService.inquireByRating(rating);
	}

}
