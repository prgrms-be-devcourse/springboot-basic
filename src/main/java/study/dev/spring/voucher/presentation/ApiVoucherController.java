package study.dev.spring.voucher.presentation;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.dto.ListWrapper;
import study.dev.spring.voucher.application.VoucherService;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class ApiVoucherController {

	private final VoucherService voucherService;

	@PostMapping
	public ResponseEntity<Void> createVoucher(
		@RequestBody CreateVoucherRequest request
	) {
		String voucherId = voucherService.createVoucher(request);

		return ResponseEntity
			.created(URI.create(String.format("/api/vouchers/%s", voucherId)))
			.build();
	}

	@GetMapping
	public ListWrapper<VoucherInfo> getAllVouchers() {
		List<VoucherInfo> result = voucherService.getAllVouchers();

		return new ListWrapper<>(result);
	}

	@GetMapping("/customer/{customerId}")
	public ListWrapper<VoucherInfo> getVouchersByCustomer(
		@PathVariable String customerId
	) {
		List<VoucherInfo> result = voucherService.getVouchersByCustomer(customerId);

		return new ListWrapper<>(result);
	}
}
