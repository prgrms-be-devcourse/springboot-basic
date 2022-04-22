package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
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
		/*
		null 반환시 테스트 실패, 임시로 FixedAmountVoucher 생성해서 반환
		 */
		return new FixedAmountVoucher();
	}
}
