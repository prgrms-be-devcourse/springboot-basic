package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.voucher.exception.ErrorMessage.VOUCHER_NOT_FOUND;

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

	@Override
	public List<Voucher> findAll() {
		return voucherRepository.findAll();
	}

	@Override
	public void deleteById(Long voucherId) {
		int count = voucherRepository.deleteById(voucherId);
		if (count != 1) {
			throw new IllegalArgumentException(VOUCHER_NOT_FOUND.getMessage());
		}
	}

	@Override
	public Voucher findById(Long voucherId) {
		return voucherRepository.findById(voucherId).orElseThrow(() -> new IllegalArgumentException(VOUCHER_NOT_FOUND.getMessage()));
	}

	@Override
	public List<Voucher> findByCreatedAt(LocalDate createdAt) {
		return voucherRepository.findByCreatedAt(createdAt);
	}

	@Override
	public List<Voucher> findByVoucherType(VoucherType voucherType) {
		return voucherRepository.findByVoucherType(voucherType);
	}

	private Voucher createVoucher(VoucherType voucherType, int discountAmount) {
		return Voucher.create(voucherType, discountAmount);
	}
}
