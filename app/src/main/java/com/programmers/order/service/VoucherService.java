package com.programmers.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Customer;
import com.programmers.order.domain.Voucher;
import com.programmers.order.repository.voucher.VoucherRepository;

@Transactional(readOnly = true)
@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Transactional
	public Voucher save(Voucher voucher) {
		return voucherRepository.saveVoucher(voucher);
	}

	public List<Voucher> lookUp() {
		return voucherRepository.getVouchers();
	}
}
