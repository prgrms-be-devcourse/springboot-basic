package com.example.voucher.service;

import java.util.List;
import java.util.UUID;

import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.PercentDiscountVoucher;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.repository.VoucherRepository;

public class VoucherService {
	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Voucher createVoucher(VoucherType voucherType, long discount) {
		Voucher voucher = null;
		if (voucherType == VoucherType.FixedAmount) {
			voucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
		} else if (voucherType == VoucherType.PercentDiscount) {
			voucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
		}

		UUID uuid = voucherRepository.save(voucher);
		return voucher;
	}

	public List<Voucher> getVouchers() {
		return voucherRepository.findAll();
	}

}
