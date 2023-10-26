package com.programmers.springbasic.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.programmers.springbasic.dto.CustomerDto;
import com.programmers.springbasic.dto.VoucherDto;
import com.programmers.springbasic.entity.voucher.VoucherType;
import com.programmers.springbasic.service.VoucherService;

@Controller
public class VoucherController {

	private final VoucherService voucherService;

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public VoucherDto createVoucher(VoucherType voucherType, long discountValue) {
		return voucherService.createVoucher(voucherType, discountValue);
	}

	public List<VoucherDto> getVouchers() {
		return voucherService.getVouchers();
	}

	public VoucherDto getVoucherDetail(UUID voucherId) {
		return voucherService.getVoucherDetail(voucherId);
	}

	public VoucherDto updateVoucher(UUID voucherId, long newDiscountValue) {
		return voucherService.updateVoucher(voucherId, newDiscountValue);
	}

	public void deleteVoucher(UUID voucherId) {
		voucherService.deleteVoucher(voucherId);
	}

	public List<CustomerDto> getCustomersByVoucher(UUID voucherId) {
		return voucherService.getCustomersByVoucher(voucherId);
	}

}
