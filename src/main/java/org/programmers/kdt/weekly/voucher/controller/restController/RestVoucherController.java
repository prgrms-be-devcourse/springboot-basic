package org.programmers.kdt.weekly.voucher.controller.restController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestVoucherController {

	private final VoucherService voucherService;

	public RestVoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResponse<String> illegalArgumentHandler(IllegalArgumentException e) {
		return ApiResponse.fail(404, e.toString());
	}

	@PostMapping("/api/v1/voucher")
	public ApiResponse<VoucherDto> create(@RequestBody CreateVoucherRequest createVoucherRequest) {
		var savedVoucherDtp = this.voucherService.save(createVoucherRequest.voucherType(),
			createVoucherRequest.value());

		return ApiResponse.ok(savedVoucherDtp);
	}

	@GetMapping("/api/v1/vouchers")
	public ApiResponse<List<Voucher>> list() {

		return ApiResponse.ok(this.voucherService.getVouchers());
	}

	@GetMapping("/api/v1/vouchers/{type}")
	public ApiResponse<List<Voucher>> voucherTypeList(@PathVariable VoucherType type) {

		return ApiResponse.ok(this.voucherService.findByType(type));
	}

	@GetMapping(value = "/api/v1/vouchers/period/{begin}/{end}")
	public ApiResponse<List<Voucher>> getVoucherByCreatedTime(
		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

		return ApiResponse.ok(this.voucherService.getVoucherByCreatedAt(begin, end));
	}

	@GetMapping("/api/v1/vouchers/{id}")
	public ApiResponse<UUID> findById(@PathVariable UUID id) {
		var foundVoucher = this.voucherService.findById(id);

		if (foundVoucher.isEmpty()) {
			return ApiResponse.fail(404, id);
		}

		return ApiResponse.ok(id);
	}

	@DeleteMapping("/api/v1/vouchers/{id}")
	public ApiResponse<UUID> delete(@PathVariable UUID id) {
		this.voucherService.deleteById(id);

		return ApiResponse.ok(id);
	}
}