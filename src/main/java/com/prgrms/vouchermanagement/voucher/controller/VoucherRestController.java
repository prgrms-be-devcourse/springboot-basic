package com.prgrms.vouchermanagement.voucher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/vouchers/")
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
}
