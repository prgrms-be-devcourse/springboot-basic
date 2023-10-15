package com.programmers.springbasic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.controller.dto.CreateFixedAmountVoucherRequest;
import com.programmers.springbasic.controller.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.springbasic.controller.dto.ListVouchersResponse;
import com.programmers.springbasic.entity.Voucher;
import com.programmers.springbasic.repository.VoucherRepository;

@Service
public class VoucherService {
	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Voucher createVoucher(Voucher voucher) {
		return voucherRepository.save(voucher);
	}

	public List<ListVouchersResponse> listVoucher() {
		List<Voucher> vouchers = voucherRepository.findAll();
		// return vouchers.stream()
		// 	.map(ListVouchersResponse::from)
		// 	.collect(Collectors.toList());
		return null;
	}

	public UUID createPercentDiscountVoucher(CreatePercentDiscountVoucherRequest request) {
		UUID voucherId = UUID.randomUUID();
		voucherRepository.save(request.toEntity(voucherId));
		return voucherId;
	}

	public UUID createFixedAmountVoucher(CreateFixedAmountVoucherRequest request) {
		UUID voucherId = UUID.randomUUID();
		voucherRepository.save(request.toEntity(voucherId));
		return voucherId;
	}

}
