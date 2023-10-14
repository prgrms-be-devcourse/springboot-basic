package com.programmers.basic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.basic.entity.Voucher;
import com.programmers.basic.repository.VoucherRepository;

@Service
public class VoucherService {
	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Voucher createVoucher(Voucher voucher) {
		return voucherRepository.save(voucher);
	}

	public List<Voucher> listVoucher() {
		return voucherRepository.findAll();
	}
}
