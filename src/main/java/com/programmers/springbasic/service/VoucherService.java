package com.programmers.springbasic.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.springbasic.dto.CreateFixedAmountVoucherRequest;
import com.programmers.springbasic.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.springbasic.dto.GetVouchersResponse;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.repository.voucher.VoucherRepository;

@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public List<GetVouchersResponse> getVouchers() {
		List<Voucher> vouchers = voucherRepository.findAll();
		return vouchers.stream().map(GetVouchersResponse::new).collect(Collectors.toList());
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
