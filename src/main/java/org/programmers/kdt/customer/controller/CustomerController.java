package org.programmers.kdt.customer.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.CustomerDto;
import org.programmers.kdt.customer.service.CustomerService;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
	private final VoucherService voucherService;
	private final CustomerService customerService;

	// 전체 회원 조회
	@GetMapping
	public String customers(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);
		return "/customers";
	}

	// 회원 상세 조회
	@GetMapping("{customerId}")
	public String customer(@PathVariable("customerId") UUID customerId, Model model) {
		Customer customer = customerService.findCustomerById(customerId).get();
		model.addAttribute("customer", customer);

		List<Voucher> vouchers = voucherService.getAllVouchersBelongsToCustomer(customer);
		model.addAttribute("vouchers", vouchers);

		List<Voucher> unownedVouchers = voucherService.getAllUnregisteredVouchers();
		model.addAttribute("unownedVouchers", unownedVouchers);

		return "/customer";
	}

	// 회원 등록 폼
	@GetMapping("/newCustomer")
	public String newCustomerForm() {
		return "/newCustomerForm";
	}

	// 회원 등록 실행
	@PostMapping("/newCustomer")
	public String addCustomer(@ModelAttribute("customerDto") CustomerDto customerDto, RedirectAttributes redirectAttributes) {
		Customer customer = customerService.signUp(customerDto.getName(), customerDto.getEmail());
		redirectAttributes.addAttribute("customerId", customer.getCustomerId());
		return "redirect:/customers/{customerId}";
	}

	// 회원 탈퇴(삭제) 기능
	@GetMapping("/{customerId}/remove")
	public String removeCustomer(@PathVariable("customerId") UUID customerId) {
		Customer customer = customerService.findCustomerById(customerId).get();
		List<Voucher> vouchers = voucherService.getAllVouchersBelongsToCustomer(customer);
		for (Voucher voucher : vouchers) {
			customerService.removeVoucherFromCustomer(customer, voucher);
		}
		customerService.removeCustomer(customerId);
		return "redirect:/customers";
	}

	// 바우처 할당 기능
	@PostMapping("/{customerId}")
	public String allocateVoucher(@PathVariable("customerId") UUID customerId, String voucherId) {
		Customer customer = customerService.findCustomerById(customerId).get();
		Voucher voucher = voucherService.getVoucher(UUID.fromString(voucherId)).get();
		customerService.addVoucherToCustomer(customer, voucher);
		return "redirect:/customers/{customerId}";
	}

	// 바우처 삭제 기능
	@PostMapping("/{customerId}/remove")
	public String deallocateVoucher(@PathVariable("customerId") UUID customerId, String removingVoucherId) {
		Customer customer = customerService.findCustomerById(customerId).get();
		customerService.removeVoucherFromCustomer(customer, UUID.fromString(removingVoucherId));
		return "redirect:/customers/{customerId}";
	}
}
