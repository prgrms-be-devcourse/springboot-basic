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

	private VoucherRepository repository;
	private VoucherFactory factory;

	@Autowired
	public VoucherService(VoucherRepository repository, VoucherFactory factory) {
		this.repository = repository;
		this.factory = factory;
	}

	public Voucher createVoucher(VoucherType voucherType, String discountAmount) {
		Voucher voucher = factory.makeVoucher(voucherType, UUID.randomUUID(), discountAmount);
		repository.save(voucher);
		return voucher;
	}

	public List<Voucher> getAllVoucher() {
		return repository.findAll();
	}
}
