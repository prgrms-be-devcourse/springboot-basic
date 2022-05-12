package com.voucher.vouchermanagement.service.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voucher.vouchermanagement.controller.api.v1.request.UpdateVoucherRequest;
import com.voucher.vouchermanagement.dto.voucher.CreateVoucherRequest;
import com.voucher.vouchermanagement.dto.voucher.VoucherDto;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;

@Service
public class VoucherService {

	private final VoucherRepository voucherRepository;
	private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	@Transactional
	public UUID create(VoucherType voucherType, Long value) throws RuntimeException {
		Voucher voucher = voucherType.create(UUID.randomUUID(), value, LocalDateTime.now());
		this.voucherRepository.insert(voucher);

		return voucher.getVoucherId();
	}

	@Transactional
	public void multiCreate(List<CreateVoucherRequest> vouchers) throws RuntimeException {
		vouchers.stream()
			.map(voucher -> VoucherType.getVoucherTypeByName(voucher.getType().getTypeName())
				.create(UUID.randomUUID(), voucher.getValue(), LocalDateTime.now()))
			.forEach(voucherRepository::insert);
	}

	@Transactional(readOnly = false)
	public VoucherDto findById(UUID id) {
		return VoucherDto.of(
			this.voucherRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Voucher 정보를 찾을 수 없습니다."))
		);
	}

	@Transactional(readOnly = false)
	public void deleteById(UUID id) {
		this.voucherRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<VoucherDto> findAll() {
		return this.voucherRepository.findAll()
			.stream()
			.map(VoucherDto::of)
			.collect(Collectors.toList());
	}

	public VoucherDto update(UpdateVoucherRequest request) {
		Voucher updatedVoucher = request.getType().create(request.getId(), request.getValue(), request.getCreatedAt());

		return VoucherDto.of(voucherRepository.update(updatedVoucher));
	}
}
