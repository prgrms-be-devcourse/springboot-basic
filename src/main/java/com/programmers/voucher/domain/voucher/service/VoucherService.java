package com.programmers.voucher.domain.voucher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;

@Service
public class VoucherService {

	private final VoucherRepository repository;
	private final VoucherFactory factory;

	@Autowired
	public VoucherService(VoucherRepository repository, VoucherFactory factory) {
		this.repository = repository;
		this.factory = factory;
	}

	public Voucher createVoucher(VoucherType voucherType, String discount) {
		Voucher voucher = factory.createVoucher(voucherType, discount);
		repository.save(voucher);
		return voucher;
	}

	public List<Voucher> getAllVoucher() {
		return repository.findAll();
	}
}
