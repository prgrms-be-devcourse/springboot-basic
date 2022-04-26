package com.example.voucher.controller;

import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.service.voucher.VoucherService;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
	private final VoucherService voucherService;

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public void save(VoucherType voucherType, int discountAmount) {
		if (voucherType != null) {
			voucherService.save(voucherType, discountAmount);
			return;
		}
		// TODO: 로그 남기기
	}
}
