package org.programmers.kdt.weekly.voucher.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.programmers.kdt.weekly.voucher.controller.restController.VoucherDto;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public VoucherDto save(VoucherType type, int value) {
		var voucher = type.create(UUID.randomUUID(), value);
		var savedVoucher = this.voucherRepository.insert(voucher);

		return new VoucherDto(savedVoucher.getVoucherId(), savedVoucher.getVoucherType(),
			savedVoucher.getValue(), savedVoucher.getCreatedAt());
	}

	public List<Voucher> getVouchers() {
		return this.voucherRepository.findAll();
	}

	public Optional<Voucher> findById(UUID id) {
		return this.voucherRepository.findById(id);
	}

	public void deleteById(UUID id) {
		this.voucherRepository.deleteById(id);
	}

	public void update(Voucher voucher) {
		this.voucherRepository.update(voucher);
	}

	public List<Voucher> findByType(VoucherType voucherType) {
		return this.voucherRepository.findByType(voucherType);
	}

	public List<Voucher> getVoucherByCreatedAt(LocalDate begin, LocalDate end) {
		return this.voucherRepository.findByCreatedAt(begin, end);
	}
}