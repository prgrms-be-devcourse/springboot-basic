package org.programmers.kdt.weekly.voucher.controller.restController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.programmers.kdt.weekly.voucher.controller.response.ApiResponse;
import org.programmers.kdt.weekly.voucher.controller.restController.VoucherDto.VoucherResponse;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherRestController {

	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@PostMapping
	public ApiResponse<VoucherResponse> create(
		@RequestBody @Valid VoucherDto.VoucherCreateRequest voucherCreateRequest) {
		var savedVoucherDto = this.voucherService.save(voucherCreateRequest.voucherType(),
			voucherCreateRequest.value());

		return ApiResponse.ok(savedVoucherDto);
	}

	@GetMapping
	public ApiResponse<List<VoucherResponse>> getVouchers() {

		return ApiResponse.ok(this.voucherService.getVouchers());
	}

	@GetMapping("/{type}")
	public ApiResponse<List<VoucherResponse>> getVouchersByType(@PathVariable VoucherType type) {

		return ApiResponse.ok(this.voucherService.findByType(type));
	}

	@GetMapping(value = "/period/{begin}/{end}")
	public ApiResponse<List<VoucherResponse>> getVouchersByCreatedTime(
		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

		return ApiResponse.ok(this.voucherService.getVoucherByCreatedAt(begin, end));
	}

	@GetMapping("/{id}")
	public ApiResponse<VoucherResponse> getVoucherById(@PathVariable UUID id) {
		var foundVoucher = this.voucherService.findById(id);

		return ApiResponse.ok(foundVoucher);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<UUID> deleteById(@PathVariable UUID id) {
		this.voucherService.deleteById(id);

		return ApiResponse.ok(id);
	}
}