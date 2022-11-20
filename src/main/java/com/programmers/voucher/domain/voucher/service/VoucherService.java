package com.programmers.voucher.domain.voucher.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;

@Service
public class VoucherService {

	private final VoucherRepository repository;

	@Autowired
	public VoucherService(VoucherRepository repository) {
		this.repository = repository;
	}

	public Voucher createVoucher(VoucherType voucherType, String discount) {
		Voucher voucher = VoucherFactory.createVoucher(voucherType, discount);
		repository.save(voucher);
		return voucher;
	}

	public Voucher findById(UUID voucherId) {
		return repository.findById(voucherId);
	}

	public List<Voucher> getAllVoucher() {
		return repository.findAll();
	}
}
