package com.voucher.vouchermanagement.domain.voucher.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voucher.vouchermanagement.domain.voucher.dto.UpdateVoucherRequest;
import com.voucher.vouchermanagement.domain.voucher.dto.CreateVoucherRequest;
import com.voucher.vouchermanagement.domain.voucher.dto.VoucherDto;
import com.voucher.vouchermanagement.domain.voucher.model.Voucher;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherCriteria;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;
import com.voucher.vouchermanagement.domain.voucher.repository.VoucherRepository;
import com.voucher.vouchermanagement.exception.DataNotFoundException;

@Service
@Transactional
@Profile("prod")
public class VoucherService {

	private final VoucherRepository voucherRepository;

	public VoucherService(VoucherRepository voucherRepository) {
		this.voucherRepository = voucherRepository;
	}

	public UUID create(VoucherType voucherType, Long value) {
		Voucher voucher = voucherType.create(UUID.randomUUID(), value, LocalDateTime.now());
		this.voucherRepository.insert(voucher);

		return voucher.getVoucherId();
	}

	public void multiCreate(List<CreateVoucherRequest> vouchers) {
		vouchers.stream()
			.map(voucher -> VoucherType.getVoucherTypeByName
					(
						voucher.getType().getTypeName()
					)
				.create(UUID.randomUUID(), voucher.getValue(), LocalDateTime.now())
			)
			.forEach(voucherRepository::insert);
	}

	@Transactional(readOnly = true)
	public VoucherDto findById(UUID id) {
		return VoucherDto.of(
			this.voucherRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Voucher 정보를 찾을 수 없습니다."))
		);
	}

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

	@Transactional(readOnly = true)
	public List<VoucherDto> findByCriteria(VoucherCriteria criteria) {
		return voucherRepository.findByCriteria(criteria)
			.stream()
			.map(VoucherDto::of)
			.collect(Collectors.toList());
	}
}
