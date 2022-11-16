package com.programmers.voucher.domain.voucher.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;

@Service
public class VoucherService {

	private VoucherRepository repository;

	@Autowired
	public VoucherService(VoucherRepository repository) {
		this.repository = repository;
	}

	public Voucher createVoucher(String discountAmount, VoucherType voucherType) {
		Voucher voucher = new Voucher(UUID.randomUUID(), voucherType, discountAmount);
		repository.save(voucher);
		return voucher;
	}

	public List<Voucher> getAllVoucher() {
		return repository.findAll();
	}
}
