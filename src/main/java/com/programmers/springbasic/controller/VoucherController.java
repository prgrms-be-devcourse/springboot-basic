package com.programmers.springbasic.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.programmers.springbasic.dto.CreateFixedAmountVoucherRequest;
import com.programmers.springbasic.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.springbasic.dto.ListVouchersResponse;
import com.programmers.springbasic.service.VoucherService;

@Controller
public class VoucherController {

	private final VoucherService voucherService;

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public UUID createPercentDiscountVoucher(CreatePercentDiscountVoucherRequest request) {
		return voucherService.createPercentDiscountVoucher(request);
	}

	public UUID createFixedAmountVoucher(CreateFixedAmountVoucherRequest request) {
		return voucherService.createFixedAmountVoucher(request);
	}

	public List<ListVouchersResponse> listVoucher() {
		return voucherService.listVoucher();
	}

}
