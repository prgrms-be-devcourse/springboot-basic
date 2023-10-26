package com.programmers.springbasic.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.programmers.springbasic.dto.CreateFixedAmountVoucherRequest;
import com.programmers.springbasic.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.springbasic.dto.GetVouchersResponse;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.service.VoucherService;

@Controller
public class VoucherController {

	private final VoucherService voucherService;

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public Voucher createPercentDiscountVoucher(CreatePercentDiscountVoucherRequest request) {
		return voucherService.createPercentDiscountVoucher(request);
	}

	public Voucher createFixedAmountVoucher(CreateFixedAmountVoucherRequest request) {
		return voucherService.createFixedAmountVoucher(request);
	}

	public List<GetVouchersResponse> getVouchers() {
		return voucherService.getVouchers();
	}

	public Voucher getVoucherDetail(UUID voucherId) {
		return voucherService.getVoucherDetail(voucherId);
	}

	public Voucher updateVoucher(UUID voucherId, long newDiscountValue) {
		return voucherService.updateVoucher(voucherId, newDiscountValue);
	}

	public void deleteVoucher(UUID voucherId) {
		voucherService.deleteVoucher(voucherId);
	}
}
