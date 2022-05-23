package org.programmers.kdt.weekly.voucher.service;

import static java.lang.String.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;
import org.programmers.kdt.weekly.voucher.controller.restController.VoucherDto.VoucherResponse;
import org.programmers.kdt.weekly.voucher.converter.VoucherConverter;
import org.programmers.kdt.weekly.voucher.exception.NotFoundEntityByIdException;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;
	private final VoucherConverter voucherConverter;

	public VoucherService(VoucherRepository voucherRepository,
		VoucherConverter voucherConverter) {
		this.voucherRepository = voucherRepository;
		this.voucherConverter = voucherConverter;
	}

	@Transactional
	public VoucherResponse save(VoucherType type, int value) {
		var voucher = type.create(UUID.randomUUID(), value);
		var savedVoucher = this.voucherRepository.insert(voucher);

		return voucherConverter.convertVoucherResponse(savedVoucher);
	}

	public List<VoucherResponse> getVouchers() {

		return this.voucherRepository.findAll()
			.stream()
			.map(voucherConverter::convertVoucherResponse)
			.collect(Collectors.toList());
	}

	public VoucherResponse findById(UUID id) {
		var foundVoucher = this.voucherRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundEntityByIdException(format("ID를 찾을 수 없습니다. -> {}", id),
					ErrorCode.NOT_FOUND_BY_VOUCHER_ID));

		return voucherConverter.convertVoucherResponse(foundVoucher);
	}

	@Transactional
	public void deleteById(UUID id) {
		this.voucherRepository.deleteById(id);
	}

	@Transactional
	public VoucherResponse update(Voucher voucher) {
		var updatedVoucher = this.voucherRepository.update(voucher);

		return voucherConverter.convertVoucherResponse(updatedVoucher);
	}

	public List<VoucherResponse> findByType(VoucherType voucherType) {
		return this.voucherRepository.findByType(voucherType)
			.stream()
			.map(voucherConverter::convertVoucherResponse)
			.collect(Collectors.toList());
	}

	public List<VoucherResponse> getVoucherByCreatedAt(LocalDate begin, LocalDate end) {
		return this.voucherRepository.findByCreatedAt(begin, end)
			.stream()
			.map(voucherConverter::convertVoucherResponse)
			.collect(Collectors.toList());
	}
}