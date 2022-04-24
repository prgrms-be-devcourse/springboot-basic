package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;


@Service
public class VoucherServiceImpl implements VoucherService {
	private final VoucherRepository voucherRepository;

	public VoucherServiceImpl(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Override
	public Voucher save(VoucherType voucherType, int discountAmount) {
		Voucher voucher = createVoucher(voucherType, discountAmount);
		return voucherRepository.save(voucher);
	}

	private Voucher createVoucher(VoucherType voucherType, int discountAmount) {
		return voucherType.create(null, discountAmount);
	}
}
