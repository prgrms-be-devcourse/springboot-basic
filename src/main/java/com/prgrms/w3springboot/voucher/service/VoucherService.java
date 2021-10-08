package com.prgrms.w3springboot.voucher.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherFactory;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.voucher.repository.VoucherRepository;

@Service
public class VoucherService {
	private final VoucherRepository voucherRepository;
	private final VoucherFactory voucherFactory = VoucherFactory.getInstance();

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public Voucher getVoucher(UUID voucherId) {
		return voucherRepository.findById(voucherId)
			.orElseThrow(
				() -> new NullPointerException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
	}

	public Voucher createVoucher(UUID voucherId, long discountAmount, VoucherType voucherType,
		LocalDateTime localDateTime) {
		Voucher voucher = voucherFactory.createVoucher(voucherId, discountAmount, voucherType, localDateTime);
		return voucherRepository.insert(voucher);
	}

	public Voucher updateVoucher(UUID voucherId, long updateAmount) {
		Optional<Voucher> existingVoucher = voucherRepository.findById(voucherId);

		if (existingVoucher.isPresent()) {
			Voucher updatingVoucher = voucherFactory.createVoucher(voucherId, updateAmount,
				existingVoucher.get().getVoucherType(), existingVoucher.get().getCreatedAt());
			return voucherRepository.update(updatingVoucher);
		}

		throw new IllegalArgumentException("유효하지 않은 입력입니다.");
	}

	public void deleteVoucher(UUID voucherId) {
		voucherRepository.delete(voucherId);
	}

	public List<Voucher> listVoucher() {
		return voucherRepository.findAll();
	}

	// 나중에 구현
	public void useVoucher(Voucher voucher) {

	}
}
