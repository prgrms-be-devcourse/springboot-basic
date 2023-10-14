package com.programmers.basic.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.programmers.basic.entity.Voucher;
import com.programmers.basic.service.VoucherService;

@Controller
public class VoucherController {

	private final VoucherService voucherService;

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public Voucher createVoucher(Voucher voucher) {
		return voucherService.createVoucher(voucher);
	}

	public List<Voucher> listVoucher() {
		return voucherService.listVoucher();
	}

}
