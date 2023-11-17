package study.dev.spring.customer.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import study.dev.spring.customer.application.CustomerService;
import study.dev.spring.customer.application.dto.CustomerInfo;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class WebCustomerController {

	private final CustomerService customerService;

	@GetMapping
	public String getAllCustomers(Model model) {
		List<CustomerInfo> result = customerService.getAllCustomers();
		model.addAttribute("customerInfoList", result);

		return "/customers/customer_info";
	}

	@GetMapping("/voucher")
	public String getCustomersByVoucher(
		@RequestParam String voucherId,
		Model model
	) {
		List<CustomerInfo> result = customerService.getCustomersByVoucher(voucherId);
		model.addAttribute("customerInfoList", result);

		return "/customers/customer_info";
	}
}
