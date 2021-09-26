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
import java.util.Optional;
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
	@GetMapping("/{customerId}")
	public String customer(@PathVariable("customerId") UUID customerId, Model model) {
		// 2021-09-27 Allen 피드백 반영
		// msg : "findCustomerById가 optional을 리턴하나요?
		//        get()을 사용하는건 Optional을 이용하는 의미가 없는 것 같아요. 인텔리제이도 아마 경고메시지를 줄 것 같네요"
		// sol) 존재하지 않는 회원 조회시 RuntimeException 을 날리도록 수정
		Customer customer = customerService.findCustomerById(customerId).orElseThrow(() -> {
			throw new RuntimeException("No such customer found. Please check customer ID again. (Given: %s)"
					.formatted(customerId.toString()));
		});
		model.addAttribute("customer", customer);

		List<Voucher> vouchers = voucherService.getAllVouchersBelongsToCustomer(customer);
		model.addAttribute("vouchers", vouchers);

		List<Voucher> unownedVouchers = voucherService.getAllUnregisteredVouchers();
		model.addAttribute("unownedVouchers", unownedVouchers);

		return "/customer";
	}

	// 회원 등록 폼
	// 2021-09-27 Allen 피드백 반영
	// msg : "url prefix에 custromers가 들어가서 new만 들어가도 자연스럽지 않나 싶습니다."
	// sol) 처음 작성시에는 newCustomer 쪽이 더 명확해 보인다고 생각했는데, 다시 살펴보니 별로 전달력의 차이가 느껴지지 않음. 보다 간결한 new 쪽이 더 나을 듯 하다고 판단.
	@GetMapping("/new")
	public String newCustomerForm() {
		return "/new";
	}

	// 회원 등록 실행
	@PostMapping("/new")
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
