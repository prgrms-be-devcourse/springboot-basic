package com.programmers.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Voucher;
import com.programmers.order.repository.VoucherRepository;

@Transactional(readOnly = true)
@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Transactional
	public Voucher create(Voucher voucher) {
		return voucherRepository.insert(voucher);
	}
}
