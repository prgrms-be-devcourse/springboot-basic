package org.programmers.kdt.weekly.voucher.controller.restController;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.programmers.kdt.weekly.voucher.controller.dto.DateRequestDto;
import org.programmers.kdt.weekly.voucher.controller.dto.VoucherDto;
import org.programmers.kdt.weekly.voucher.controller.response.ApiResponse;
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
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@PostMapping
	public ApiResponse<VoucherDto.Response> create(
		@RequestBody @Valid VoucherDto.CreateRequest createRequest) {
		return ApiResponse.create(this.voucherService.save(createRequest.voucherType(),
			createRequest.value()));
	}

	@GetMapping
	public ApiResponse<List<VoucherDto.Response>> getVouchers() {
		return ApiResponse.ok(this.voucherService.getAll());
	}

	@GetMapping("/type/{type}")
	public ApiResponse<List<VoucherDto.Response>> getVouchersByType(@PathVariable VoucherType type) {
		return ApiResponse.ok(this.voucherService.getByType(type));
	}

	@GetMapping("/created-at")
	public ApiResponse<List<VoucherDto.Response>> getVouchersByCreatedTime(
		@Valid DateRequestDto dateRequestDto) {

		return ApiResponse.ok(this.voucherService.getByCreatedAt(dateRequestDto.begin(), dateRequestDto.end()));
	}

	@GetMapping("/{id}")
	public ApiResponse<VoucherDto.Response> getVoucherById(@PathVariable UUID id) {
		return ApiResponse.ok(this.voucherService.getById(id));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<UUID> deleteById(@PathVariable UUID id) {
		this.voucherService.deleteById(id);

		return ApiResponse.ok(id);
	}
}