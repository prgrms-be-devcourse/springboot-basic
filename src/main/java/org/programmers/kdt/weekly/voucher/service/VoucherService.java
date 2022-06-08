package org.programmers.kdt.weekly.voucher.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.programmers.kdt.weekly.voucher.controller.dto.VoucherDto;
import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;
import org.programmers.kdt.weekly.voucher.exception.NotFoundEntityByIdException;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Transactional
	public VoucherDto.Response save(VoucherType type, int value) {
		var voucher = type.create(UUID.randomUUID(), value);
		var savedVoucher = this.voucherRepository.insert(voucher);

		return VoucherDto.Response.from(savedVoucher);
	}

	public List<VoucherDto.Response> getAll() {
		return this.voucherRepository.findAll()
			.stream()
			.map(VoucherDto.Response::from)
			.toList();
	}

	public VoucherDto.Response getById(UUID id) {
		var foundVoucher = this.voucherRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundEntityByIdException("해당 voucher ID를 찾을 수 없습니다. -> " + id,
					ErrorCode.NOT_FOUND_VOUCHER_BY_ID));

		return VoucherDto.Response.from(foundVoucher);
	}

	@Transactional
	public void deleteById(UUID id) {
		this.voucherRepository.deleteById(id);
	}

	@Transactional
	public VoucherDto.Response update(UUID id, int value) {
		var foundVoucher = this.voucherRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundEntityByIdException("해당 voucher ID를 찾을 수 없습니다. -> " + id,
					ErrorCode.NOT_FOUND_VOUCHER_BY_ID))
			.changeValue(value);
		var updatedVoucher = voucherRepository.update(foundVoucher);

		return VoucherDto.Response.from(updatedVoucher);
	}

	public List<VoucherDto.Response> getByType(VoucherType voucherType) {
		return this.voucherRepository.findByType(voucherType)
			.stream()
			.map(VoucherDto.Response::from)
			.toList();
	}

	public List<VoucherDto.Response> getByCreatedAt(LocalDate begin, LocalDate end) {
		return this.voucherRepository.findByCreatedAt(begin, end)
			.stream()
			.map(VoucherDto.Response::from)
			.toList();
	}
}