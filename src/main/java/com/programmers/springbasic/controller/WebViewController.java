package com.programmers.springbasic.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebViewController {

	@GetMapping
	public String home() {
		return "index";
	}

	@GetMapping("/create-voucher")
	public String createVoucher() {
		return "voucher/create";
	}

	@GetMapping("/voucher-detail/{voucherId}")
	public String getVoucherDetail(@PathVariable UUID voucherId, Model model) {
		model.addAttribute("voucherId", voucherId);
		return "voucher/detail";
	}

	@GetMapping("/customer-list")
	public String getCustomers() {
		return "customer/list";
	}

	@GetMapping("/customer-detail/{customerId}")
	public String getCustomerDetail(@PathVariable UUID customerId, Model model) {
		model.addAttribute("customerId", customerId);
		return "customer/detail";
	}

}
