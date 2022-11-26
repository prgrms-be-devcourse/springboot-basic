package com.programmers.voucher.web.controller.customer;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.domain.customer.service.CustomerWebService;
import com.programmers.voucher.web.controller.customer.dto.CustomerRequestDto;
import com.programmers.voucher.web.controller.customer.dto.CustomerResponseDto;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerWebService customerWebService;

	public CustomerController(CustomerWebService customerWebService) {
		this.customerWebService = customerWebService;
	}

	@GetMapping
	public String customerHome() {
		return "customer/customerHome";
	}

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("customer", new CustomerRequestDto());
		model.addAttribute("customerTypes", CustomerType.values());
		return "customer/addForm";
	}

	@PostMapping("/add")
	public String addCustomer(@ModelAttribute("customer") CustomerRequestDto customerRequestDto,
		RedirectAttributes redirectAttributes) {
		CustomerResponseDto customerResponseDto = customerWebService.createCustomer(customerRequestDto);
		redirectAttributes.addAttribute("customerId", customerResponseDto.getCustomerId());
		return "redirect:/customer/{customerId}";
	}

	@GetMapping("/{customerId}/edit")
	public String editForm(@PathVariable UUID customerId, Model model) {
		CustomerResponseDto customerResponseDto = customerWebService.findById(customerId);
		model.addAttribute("customer", customerResponseDto);
		model.addAttribute("customerTypes", CustomerType.values());
		return "customer/editForm";
	}

	@PostMapping("/{customerId}/edit")
	public String editCustomer(@PathVariable UUID customerId,
		@ModelAttribute("customer") CustomerRequestDto customerRequestDto, RedirectAttributes redirectAttributes) {
		customerWebService.modifyCustomer(customerId, customerRequestDto);
		redirectAttributes.addAttribute("customerId", customerId);
		return "redirect:/customer/{customerId}";
	}

	@GetMapping("/{customerId}")
	public String customer(@PathVariable UUID customerId, Model model) {
		CustomerResponseDto customerResponseDto = customerWebService.findById(customerId);
		model.addAttribute("customer", customerResponseDto);
		return "customer/customer";
	}

	@GetMapping("/all")
	public String allCustomers(Model model) {
		List<CustomerResponseDto> customerResponseDtoList = customerWebService.getAllCustomer();
		model.addAttribute("customers", customerResponseDtoList);
		return "customer/customers";
	}

	@GetMapping("/blacklists")
	public String blacklists(Model model) {
		List<CustomerResponseDto> blacklists = customerWebService.getBlacklist();
		model.addAttribute("customers", blacklists);
		return "customer/customers";
	}

	@GetMapping("/delete")
	public String removeForm(Model model) {
		List<CustomerResponseDto> customerResponseDtoList = customerWebService.getAllCustomer();
		model.addAttribute("customers", customerResponseDtoList);
		return "customer/deleteForm";
	}

	@DeleteMapping("/delete/{customerId}")
	public String removeCustomer(@PathVariable UUID customerId) {
		customerWebService.removeCustomer(customerId);
		return "redirect:/customer/delete";
	}
}
