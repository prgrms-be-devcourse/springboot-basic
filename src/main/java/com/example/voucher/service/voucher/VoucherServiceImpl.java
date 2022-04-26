package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.PercentDiscountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;

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
		if (voucherType == FIXED_AMOUNT_VOUCHER) {
			return new FixedAmountVoucher(discountAmount);
		}
		return new PercentDiscountVoucher(discountAmount);
	}
}
