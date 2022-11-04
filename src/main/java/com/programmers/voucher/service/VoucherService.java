package com.programmers.voucher.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.repository.voucher.VoucherRepository;

@Service
public class VoucherService {

	private VoucherRepository repository;
	private VoucherFactory factory;

	@Autowired
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
