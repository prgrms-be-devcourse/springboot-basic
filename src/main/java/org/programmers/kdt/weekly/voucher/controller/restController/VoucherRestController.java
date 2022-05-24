package org.programmers.kdt.weekly.voucher.controller.restController;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.programmers.kdt.weekly.voucher.controller.VoucherDto;
import org.programmers.kdt.weekly.voucher.controller.response.ApiResponse;
import org.programmers.kdt.weekly.voucher.exception.InvalidDateRequestException;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
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
	public ApiResponse<VoucherDto.Response> create(
		@RequestBody @Valid VoucherDto.CreateRequest createRequest) {

		return ApiResponse.ok(this.voucherService.save(createRequest.voucherType(),
			createRequest.value()));
	}

	@GetMapping
	public ApiResponse<List<VoucherDto.Response>> getVouchers() {

		return ApiResponse.ok(this.voucherService.getVouchers());
	}

	@GetMapping("/type/{type}")
	public ApiResponse<List<VoucherDto.Response>> getVouchersByType(@PathVariable VoucherType type) {

		return ApiResponse.ok(this.voucherService.getVoucherByType(type));
	}

	@GetMapping(value = "/period")
	public ApiResponse<List<VoucherDto.Response>> getVouchersByCreatedTime(
		@RequestBody @Valid VoucherDto.DateRequest dateRequest) {

		if (dateRequest.begin().isAfter(dateRequest.end())) {
			throw new InvalidDateRequestException("종료 날짜가 시작 날짜 이전일 수 없습니다.");
		}

		return ApiResponse.ok(this.voucherService.getVoucherByCreatedAt(dateRequest.begin(), dateRequest.end()));
	}

	@GetMapping("/{id}")
	public ApiResponse<VoucherDto.Response> getVoucherById(@PathVariable UUID id) {

		return ApiResponse.ok(this.voucherService.getVoucherById(id));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<UUID> deleteById(@PathVariable UUID id) {
		this.voucherService.deleteById(id);

		return ApiResponse.ok(id);
	}
}