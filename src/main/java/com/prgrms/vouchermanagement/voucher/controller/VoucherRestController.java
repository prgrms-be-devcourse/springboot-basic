package com.prgrms.vouchermanagement.voucher.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.vouchermanagement.commons.page.Pageable;
import com.prgrms.vouchermanagement.commons.api.ApiResult;
import com.prgrms.vouchermanagement.voucher.VoucherService;
import com.prgrms.vouchermanagement.voucher.dto.VoucherInfo;

@RestController
@RequestMapping("/api")
public class VoucherRestController {
	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	private final VoucherService voucherService;

	@PostMapping("/vouchers")
	public ApiResult<VoucherInfo> newVoucher(@RequestBody CreateVoucherRequest request) {
		return ApiResult.success(
			HttpStatus.OK,
			VoucherInfo.fromEntity(
				voucherService.create(
					request.getType(),
					request.getDiscountInfo())
			)
		);
	}

	@GetMapping("/vouchers")
	public ApiResult<List<VoucherInfo>> showVouchers(Pageable page) {
		return ApiResult.success(
			HttpStatus.OK,
			voucherService.getAll(page).stream()
				.map(VoucherInfo::fromEntity)
				.collect(Collectors.toList())
		);
	}

	@DeleteMapping("/vouchers/{voucherId}")
	public ApiResult deleteVoucher(@PathVariable UUID voucherId) {
		voucherService.removeById(voucherId);

		return ApiResult.success(HttpStatus.NO_CONTENT, null);
	}

	@GetMapping("/vouchers/{voucherId}")
	public ApiResult<VoucherInfo> showVoucher(@PathVariable UUID voucherId) {
		return ApiResult.success(
			HttpStatus.OK,
			VoucherInfo.fromEntity(voucherService.getById(voucherId))
		);
	}
}
