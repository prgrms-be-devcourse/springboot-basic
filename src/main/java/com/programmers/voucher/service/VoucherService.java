package com.programmers.voucher.service;

import java.util.List;
import java.util.UUID;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherFactory;
import com.programmers.voucher.repository.VoucherRepository;

public class VoucherService {

	private VoucherRepository repository;
	private VoucherFactory factory;

	public VoucherService(VoucherRepository repository, VoucherFactory factory) {
		this.repository = repository;
		this.factory = factory;
	}

	public Voucher createVoucher(String voucherType, int discountAmount) {
		Voucher voucher = factory.makeVoucher(voucherType, UUID.randomUUID(), discountAmount);
		repository.save(voucher);
		return voucher;
	}

	public List<Voucher> getAllVoucher() {
		return repository.findAll();
	}
}
