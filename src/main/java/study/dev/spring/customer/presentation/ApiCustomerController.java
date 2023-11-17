package study.dev.spring.customer.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.dto.ListWrapper;
import study.dev.spring.customer.application.CustomerService;
import study.dev.spring.customer.application.dto.CustomerInfo;
import study.dev.spring.customer.application.dto.VoucherIdRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class ApiCustomerController {

	private final CustomerService customerService;

	@GetMapping
	public ListWrapper<CustomerInfo> getAllCustomers() {
		List<CustomerInfo> result = customerService.getAllCustomers();

		return new ListWrapper<>(result);
	}

	@GetMapping("/voucher")
	public ListWrapper<CustomerInfo> getCustomersByVoucher(
		@RequestBody VoucherIdRequest request
	) {
		List<CustomerInfo> result = customerService.getCustomersByVoucher(request.voucherId());

		return new ListWrapper<>(result);
	}
}
